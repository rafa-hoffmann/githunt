package com.sonder.githunt.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owners")
data class OwnerEntity(
    @PrimaryKey val id: Long,
    val login: String,
    val avatarUrl: String,
)
