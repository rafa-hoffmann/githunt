package com.sonder.githunt.core.database.model

import androidx.room.Embedded
import com.sonder.githunt.core.model.data.Owner
import com.sonder.githunt.core.model.data.Repository

data class RepositoryWithOwner(
    @Embedded val repository: RepositoryEntity,
    @Embedded val owner: OwnerEntity
)

fun RepositoryWithOwner.asExternalModel() =
    Repository(
        id = repository.repositoryId,
        name = repository.name,
        stargazersCount = repository.stargazersCount,
        forksCount = repository.forksCount,
        owner = Owner(
            ownerId = owner.id,
            login = owner.login,
            avatarUrl = owner.avatarUrl
        ),
        createdAt = repository.createdAt,
        updatedAt = repository.updatedAt
    )
