package com.sonder.githunt.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(
    tableName = "repositories"
)
data class RepositoryEntity(
    @PrimaryKey val repositoryId: Long,
    val name: String,
    val stargazersCount: Long,
    val forksCount: Long,
    val createdAt: Instant,
    val updatedAt: Instant,
    val ownerId: Long
)
