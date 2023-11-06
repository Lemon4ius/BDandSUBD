package com.example.bdandsubd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.ActivityMainBinding
import com.example.bdandsubd.entities.getter.HotelGet
import com.example.bdandsubd.navigation.GuestlNavigation
import com.example.bdandsubd.navigation.HotelNavigation
import com.example.bdandsubd.navigation.RoomlNavigation
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection


class MainActivity : AppCompatActivity(),MainNavigation,HotelNavigation,RoomlNavigation,GuestlNavigation {
    val router: Router=App.INSTANCE.router
    lateinit var binding: ActivityMainBinding
    var connection: Connection? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigation()
        CoroutineScope(Dispatchers.IO).launch {
            DbWorker.build(applicationContext)
//            val dateTime = LocalDate.now()
//            DbWorker.newsDao.insertDataInHotel(Hotel(null,"Hotel Eleon","Minsk",5, 300))
//            DbWorker.newsDao.insertDataInHotel(Hotel(null,"Best Hotel","Minsk",1, 30))
//            DbWorker.newsDao.insertDataInHotel(Hotel(null,"Best of the best Hotel","Minsk",2, 30))
//            DbWorker.newsDao.insertDataInHotel(Hotel(null,"Уютный Уголок","Brest",3, 35))
//            DbWorker.newsDao.insertDataInHotel(Hotel(null,"Исторический Дворец","Vitebsk",5, 305))
//            DbWorker.newsDao.insertDataInHotel(Hotel(null,"Путешественник","Minsk",3, 93))
//            DbWorker.newsDao.insertDataFromGuest(Guest(null, "Ameer Akhmed", "mail1@.com",dateTime,dateTime.plusDays(7)))
//            DbWorker.newsDao.insertDataFromGuest(Guest(null, "Alex Stolpner", "mail2@.com",dateTime,dateTime.plusDays(3)))
//            DbWorker.newsDao.insertDataFromGuest(Guest(null, "Nikita Juke", "mail3@.com",dateTime,dateTime.plusDays(5)))
//            DbWorker.newsDao.insertDataFromGuest(Guest(null, "Danel Mironchick", "mail4@.com",dateTime,dateTime.plusDays(2)))
//            DbWorker.newsDao.insertDataFromGuest(Guest(null, "Yri Shupilkin", "mail5@.com",dateTime,dateTime.plusDays(10)))
//            DbWorker.newsDao.insertDtaIntoRoom(Room(null,1,1,101,"Luxe",3500.0))
//            DbWorker.newsDao.insertDtaIntoRoom(Room(null,2,2,204,"Standart",1000.0))
//            DbWorker.newsDao.insertDtaIntoRoom(Room(null,3,3,539,"Comfort",2300.0))
//            DbWorker.newsDao.insertDtaIntoRoom(Room(null,4,4,112,"Standart",1030.0))
//            DbWorker.newsDao.insertDtaIntoRoom(Room(null,5,5,113,"Comfort",2300.0))
        }
        val navigatorHolder = App.INSTANCE.navigatorHolder
        val navigator = AppNavigator(this, R.id.frameLayout, supportFragmentManager)
        navigatorHolder.setNavigator(navigator)
        router.newRootScreen(Screen.TabledListFrag())
    }

    private fun bottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener{item->
            when(item.itemId){
                R.id.page1->{
                    router.newRootScreen(Screen.TabledListFrag())
                    true
                }
                R.id.page2->{
                    router.navigateTo(Screen.ItemExporFragment())
                    true
                }
                R.id.page3->{
                    router.newRootScreen(Screen.ItemDiagramBarFragment())
                    true
                }
                R.id.page4->{
                    router.newRootScreen(Screen.ItemDiagramFragment())
                    true
                }
                else->false
            }
        }
    }

    override fun goToHotel() {
        router.navigateTo(Screen.ItemHotelFrag())
    }

    override fun goToRoom() {
        router.navigateTo(Screen.ItemRoomListFrag())
    }

    override fun goToGuest() {
        router.navigateTo(Screen.ItemsListFrag())
    }

    override fun openSearch() {
        router.navigateTo(Screen.ItemSearchFragment())
    }

    override fun openGuestSearch() {
        router.navigateTo(Screen.ItemSearchGuestFragment())
    }

    override fun openRoomtSearch() {
        router.navigateTo(Screen.ItemSearchRoomFragment())
    }
}