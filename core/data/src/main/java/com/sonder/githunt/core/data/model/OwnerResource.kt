package com.sonder.githunt.core.data.model

import com.sonder.githunt.core.database.model.OwnerEntity
import com.sonder.githunt.core.network.model.OwnerResource

fun OwnerResource.asEntity() = OwnerEntity(
    id = id,
    login = login,
    avatarUrl = avatarUrl
)
