package com.sonder.githunt.core.network.model

import com.sonder.githunt.core.network.model.util.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryResource(
    val id: Long,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("stargazers_count")
    val stargazersCount: Long,
    @SerialName("forks_count")
    val forksCount: Long,
    val owner: OwnerResource,
    @Serializable(InstantSerializer::class)
    @SerialName("created_at")
    val createdAt: Instant,
    @Serializable(InstantSerializer::class)
    @SerialName("updated_at")
    val updatedAt: Instant,
)
