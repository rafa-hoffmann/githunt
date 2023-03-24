package com.sonder.githunt.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sonder.githunt.core.data.model.asEntity
import com.sonder.githunt.core.database.GitHuntDatabase
import com.sonder.githunt.core.database.model.RemoteKeysEntity
import com.sonder.githunt.core.database.model.RepositoryWithOwner
import com.sonder.githunt.core.network.NetworkDataSource

private const val GITHUB_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class GitHuntRemoteMediator(
    private val network: NetworkDataSource,
    private val db: GitHuntDatabase
) : RemoteMediator<Int, RepositoryWithOwner>() {

    override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryWithOwner>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse =
                network.getRepositories(page, state.config.pageSize)

            val endOfPaginationReached = apiResponse.isEmpty()
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeysDao().clearRemoteKeys()
                    db.repositoriesDao().clearRepositories()
                    db.ownersDao().clearRepositories()
                }
                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = apiResponse.map {
                    RemoteKeysEntity(repositoryId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteKeysDao().insertAll(keys)
                db.ownersDao().insertAll(apiResponse.map { it.owner.asEntity() })
                db.repositoriesDao().insertAll(apiResponse.map { it.asEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RepositoryWithOwner>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repositoryWithOwner ->
                db.remoteKeysDao().remoteKeysRepoId(repositoryWithOwner.repository.repositoryId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RepositoryWithOwner>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repositoryWithOwner ->
                db.remoteKeysDao().remoteKeysRepoId(repositoryWithOwner.repository.repositoryId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RepositoryWithOwner>
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.repository?.repositoryId?.let { id ->
                db.remoteKeysDao().remoteKeysRepoId(id)
            }
        }
    }
}
