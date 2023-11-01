package com.example.bdandsubd.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bdandsubd.dataBase.ConvertersDate
import com.example.bdandsubd.entities.Guest
import com.example.bdandsubd.entities.Hotel
import com.example.bdandsubd.entities.Room
import com.example.bdandsubd.navigation.DaoData

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
    abstract fun daoController(): DaoData
}