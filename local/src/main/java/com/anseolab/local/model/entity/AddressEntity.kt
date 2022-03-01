package com.anseolab.local.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Address")
data class AddressEntity(
    @PrimaryKey val address: String,
    val createdAt: Long
)