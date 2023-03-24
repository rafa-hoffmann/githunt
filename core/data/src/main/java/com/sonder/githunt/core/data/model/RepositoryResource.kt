package com.sonder.githunt.core.data.model

import com.sonder.githunt.core.database.model.RepositoryEntity
import com.sonder.githunt.core.network.model.RepositoryResource

fun RepositoryResource.asEntity() = RepositoryEntity(
    repositoryId = id,
    name = fullName,
    stargazersCount = stargazersCount,
    forksCount = forksCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
    ownerId = owner.id
)
