package com.example.bdandsubd.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Hotel",
    indices = [Index(
        value = ["name"],
        unique = true
    )]
)
data class Hotel(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name:String,
    val address: String,
    val starRating: Int,
    val roomCount: Int
)