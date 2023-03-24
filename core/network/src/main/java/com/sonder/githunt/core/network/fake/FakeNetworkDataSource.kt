package com.sonder.githunt.core.network.fake

import JvmUnitTestFakeAssetManager
import com.sonder.githunt.core.common.network.Dispatcher
import com.sonder.githunt.core.common.network.GitHuntDispatchers.IO
import com.sonder.githunt.core.network.NetworkDataSource
import com.sonder.githunt.core.network.model.RepositoryResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class FakeNetworkDataSource @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: FakeAssetManager = JvmUnitTestFakeAssetManager,
) : NetworkDataSource {

    companion object {
        private const val REPOSITORIES_JSON = "repositories.json"
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getRepositories(page: Int, itemsPerPage: Int): List<RepositoryResource> =
        withContext(ioDispatcher) {
            assets.open(REPOSITORIES_JSON).use(networkJson::decodeFromStream)
        }
}
