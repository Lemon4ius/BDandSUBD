package com.example.bdandsubd.dataBase

import android.content.Context
import androidx.room.Room
import com.example.bdandsubd.navigation.DaoData

object DbWorker {
    lateinit var applicationContext: Context
    val appDatabase: AppDateBase by lazy {
        Room.databaseBuilder(applicationContext, AppDateBase::class.java, "aston_db").addMigrations().build()
    }
    val newsDao: DaoData by lazy {
        appDatabase.daoController()
    }
    fun build(context: Context) {
        applicationContext = context
    }
}