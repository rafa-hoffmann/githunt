package com.sonder.githunt.core.data.di

import com.sonder.githunt.core.data.repository.GitHuntRepository
import com.sonder.githunt.core.data.repository.OfflineFirstGitHuntRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsGitHuntRepository(
        gitHuntRepository: OfflineFirstGitHuntRepository,
    ): GitHuntRepository
}
