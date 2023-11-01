package com.example.bdandsubd.entities.getter

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable


data class HotelGet(
    val id: Int?,
    val name:String,
    val address: String,
    val starRating: Int,
    val roomCount: Int,
    var isChecked:Boolean?=false
):Serializable