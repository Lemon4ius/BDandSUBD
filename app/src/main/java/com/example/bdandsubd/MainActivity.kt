package com.example.bdandsubd

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bdandsubd.databinding.ActivityMainBinding
import com.example.bdandsubd.entities.Guest
import com.example.bdandsubd.entities.Hotel
import com.example.bdandsubd.entities.Room
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection
import java.time.LocalDateTime


class MainActivity : AppCompatActivity(),MainNavigation {
    val router: Router=App.INSTANCE.router
    lateinit var binding: ActivityMainBinding
    var connection: Connection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch {
            DbWorker.build(applicationContext)
 
        }
        val navigatorHolder = App.INSTANCE.navigatorHolder
        val navigator = AppNavigator(this, R.id.frameLayout, supportFragmentManager)
        navigatorHolder.setNavigator(navigator)
        router.navigateTo(Screen.TabledListFrag())
    }

    override fun goToHotel() {

    }

    override fun goToRoom() {

    }

    override fun goToGuest() {
        router.navigateTo(Screen.ItemsListFrag())
    }
}