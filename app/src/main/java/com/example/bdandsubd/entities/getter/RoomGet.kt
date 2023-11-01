package com.example.bdandsubd.entities.getter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable


data class RoomGet(
    val id: Int?,
    val nameHotel: String,
    val nameGuest: String,
    val roomNumber: Int,
    val type: String,
    val price: Double,
    var isChecked:Boolean?=false
): Serializable