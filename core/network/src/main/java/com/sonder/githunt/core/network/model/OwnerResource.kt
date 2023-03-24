package com.sonder.githunt.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerResource(
    val login: String,
    val id: Long,
    @SerialName("avatar_url")
    val avatarUrl: String
)
