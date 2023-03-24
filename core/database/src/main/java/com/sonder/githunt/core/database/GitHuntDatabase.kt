package com.sonder.githunt.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sonder.githunt.core.database.dao.OwnersDao
import com.sonder.githunt.core.database.dao.RemoteKeysDao
import com.sonder.githunt.core.database.dao.RepositoriesDao
import com.sonder.githunt.core.database.model.OwnerEntity
import com.sonder.githunt.core.database.model.RemoteKeysEntity
import com.sonder.githunt.core.database.model.RepositoryEntity
import com.sonder.githunt.core.database.util.InstantConverter

@Database(
    entities = [
        OwnerEntity::class,
        RepositoryEntity::class,
        RemoteKeysEntity::class
    ],
    version = 1
)
@TypeConverters(InstantConverter::class)
abstract class GitHuntDatabase : RoomDatabase() {
    abstract fun ownersDao(): OwnersDao
    abstract fun repositoriesDao(): RepositoriesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
