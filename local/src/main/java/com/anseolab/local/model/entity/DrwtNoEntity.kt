package com.anseolab.local.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DrwtNoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val drwtNo1: Int,
    val drwtNo2: Int,
    val drwtNo3: Int,
    val drwtNo4: Int,
    val drwtNo5: Int,
    val drwtNo6: Int,
    val bnusNo: Int,
    val createdAt: Long
)