package com.sonder.githunt.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sonder.githunt.core.database.model.OwnerEntity

@Dao
interface OwnersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repositories: List<OwnerEntity>)

    @Query("DELETE FROM owners")
    suspend fun clearRepositories()
}
