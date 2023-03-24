package com.sonder.githunt.core.model.data

import kotlinx.datetime.Instant

data class Repository(
    val id: Long,
    val name: String,
    val stargazersCount: Long,
    val forksCount: Long,
    val owner: Owner,
    val createdAt: Instant,
    val updatedAt: Instant
)
