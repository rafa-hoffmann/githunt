package com.sonder.githunt.core.data.repository

import androidx.paging.PagingData
import com.sonder.githunt.core.model.data.Repository
import kotlinx.coroutines.flow.Flow

interface GitHuntRepository {

    suspend fun getRepositories(): Flow<PagingData<Repository>>
}
