package com.example.bdandsubd.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Hotel"
)
data class Hotel(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name:String,
    val address: String,
    val starRating: Int,
    val roomCount: Int
)