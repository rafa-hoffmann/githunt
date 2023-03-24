package com.sonder.githunt.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey val repositoryId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
