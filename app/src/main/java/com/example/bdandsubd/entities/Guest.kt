package com.example.bdandsubd.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(
    tableName = "Guest",
    indices = [Index(
        value = ["email"],
        unique = true
    )]
)
data class Guest(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String,
    val email: String,
    val checkInDate: LocalDate,
    val checkOutDate:LocalDate,
)