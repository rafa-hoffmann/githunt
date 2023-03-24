package com.sonder.githunt.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sonder.githunt.core.network.BuildConfig
import com.sonder.githunt.core.network.NetworkDataSource
import com.sonder.githunt.core.network.model.RepositoryResource
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface GitHubApi {
    @GET(value = "repositories?q=language:kotlin&sort=stars")
    suspend fun getRepositories(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): NetworkResponse<List<RepositoryResource>>
}

private const val GitHubBaseUrl = BuildConfig.BACKEND_URL

@Serializable
private data class NetworkResponse<T>(
    val items: T,
)

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : NetworkDataSource {

    private val gitHubApi = Retrofit.Builder()
        .baseUrl(GitHubBaseUrl)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(GitHubApi::class.java)

    override suspend fun getRepositories(
        page: Int,
        itemsPerPage: Int
    ) = gitHubApi.getRepositories(page, itemsPerPage).items
}
