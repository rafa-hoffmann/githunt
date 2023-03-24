package com.sonder.githunt.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sonder.githunt.core.database.model.RepositoryEntity
import com.sonder.githunt.core.database.model.RepositoryWithOwner

@Dao
interface RepositoriesDao {

    @Transaction
    @Query("SELECT * FROM repositories LEFT JOIN owners ON repositories.ownerId = owners.id ORDER BY repositories.stargazersCount DESC")
    fun getRepositoriesWithOwners(): PagingSource<Int, RepositoryWithOwner>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repositories: List<RepositoryEntity>)

    @Query("DELETE FROM repositories")
    suspend fun clearRepositories()
}
