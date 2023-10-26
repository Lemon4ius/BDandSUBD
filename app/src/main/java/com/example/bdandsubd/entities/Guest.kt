package com.example.bdandsubd.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "Guest",
)
data class Guest(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String,
    val email: String,
    val checkInDate: LocalDateTime,
    val checkOutDate:LocalDateTime
)