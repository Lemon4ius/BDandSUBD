package com.example.bdandsubd

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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