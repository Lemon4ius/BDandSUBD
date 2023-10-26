package com.example.bdandsubd

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bdandsubd.entities.Guest
import com.example.bdandsubd.entities.Hotel
import com.example.bdandsubd.entities.Room

@Database(
    version = 1,
    entities =[
        Guest::class,
        Hotel::class,
        Room::class
    ]
)
@TypeConverters(ConvertersDate::class)
abstract class AppDateBase:RoomDatabase() {
    abstract fun daoController():DaoData
}