package com.sonder.githunt.core.network.di

import com.sonder.githunt.core.network.NetworkDataSource
import com.sonder.githunt.core.network.retrofit.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RetrofitNetworkModule {

    @Binds
    fun RetrofitNetwork.binds(): NetworkDataSource
}
