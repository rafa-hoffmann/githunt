package com.sonder.githunt.core.database

import com.sonder.githunt.core.database.dao.OwnersDao
import com.sonder.githunt.core.database.dao.RemoteKeysDao
import com.sonder.githunt.core.database.dao.RepositoriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesOwnersDao(
        database: GitHuntDatabase,
    ): OwnersDao = database.ownersDao()

    @Provides
    fun providesRepositoriesDao(
        database: GitHuntDatabase,
    ): RepositoriesDao = database.repositoriesDao()

    @Provides
    fun providesRemoteKeysDao(
        database: GitHuntDatabase,
    ): RemoteKeysDao = database.remoteKeysDao()
}
