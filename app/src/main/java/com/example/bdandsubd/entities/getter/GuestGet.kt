package com.example.bdandsubd.entities.getter

import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

data class GuestGet (
    val id: Int,
    val name: String,
    val email: String,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    var isChecked:Boolean?=false
)