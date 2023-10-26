package com.example.bdandsubd.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "Room",
    foreignKeys = [
        ForeignKey(
            entity = Guest::class,
            childColumns = ["guest_id"],
            parentColumns = ["id"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Hotel::class,
            childColumns = ["hotel_id"],
            parentColumns = ["id"],
            onDelete = CASCADE
        )
    ]
)
data class Room(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "hotel_id") val idHotel:Int,
    @ColumnInfo(name = "guest_id") val idGuest: Int,
    val roomNumber:Int,
    val type: String,
    val price: Double,
)