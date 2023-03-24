package com.sonder.githunt.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sonder.githunt.core.database.GitHuntDatabase
import com.sonder.githunt.core.database.model.asExternalModel
import com.sonder.githunt.core.model.data.Repository
import com.sonder.githunt.core.network.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstGitHuntRepository @Inject constructor(
    private val db: GitHuntDatabase,
    private val network: NetworkDataSource,
) : GitHuntRepository {
    override suspend fun getRepositories(): Flow<PagingData<Repository>> {

        val pagingSourceFactory = { db.repositoriesDao().getRepositoriesWithOwners() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = GitHuntRemoteMediator(network, db)
        ).flow.map { it.map { repositoryWithOwner -> repositoryWithOwner.asExternalModel() } }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }
}
