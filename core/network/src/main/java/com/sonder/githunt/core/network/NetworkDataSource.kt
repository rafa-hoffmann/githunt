package com.sonder.githunt.core.network

import com.sonder.githunt.core.network.model.RepositoryResource

interface NetworkDataSource {
    suspend fun getRepositories(page: Int, itemsPerPage: Int): List<RepositoryResource>
}
