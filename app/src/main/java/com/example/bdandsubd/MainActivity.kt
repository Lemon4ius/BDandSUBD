package com.example.bdandsubd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.ActivityMainBinding
import com.example.bdandsubd.entities.getter.HotelGet
import com.example.bdandsubd.navigation.GuestlNavigation
import com.example.bdandsubd.navigation.HotelNavigation
import com.example.bdandsubd.navigation.RoomlNavigation
import com.example.bdandsubd.presenter.SearchFragment
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
    lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigation()
        CoroutineScope(Dispatchers.IO).launch {
            DbWorker.build(applicationContext)
        }
//        val navigatorHolder = App.INSTANCE.navigatorHolder
//        val navigator = AppNavigator(this, R.id.frameLayout, supportFragmentManager)
//        navigatorHolder.setNavigator(navigator)
//        router.newRootScreen(Screen.TabledListFrag())
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

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
        navController.navigate(R.id.action_listTablesFragment_to_hotelListFragment)
//        router.navigateTo(Screen.ItemHotelFrag())
    }

    override fun goToRoom() {
        navController.navigate(R.id.action_listTablesFragment_to_roomListFragment)
//        router.navigateTo(Screen.ItemRoomListFrag())
    }

    override fun goToGuest() {
        navController.navigate(R.id.action_listTablesFragment_to_guestListFragment)
//        router.navigateTo(Screen.ItemsListFrag())
    }

    override fun openSearch() {
        navController.navigate(R.id.action_hotelListFragment_to_searchFragment, bundleOf(SearchFragment.BUNDLE_INSTANCE_SEARCH_HOTEL_KEY to 1))
//        router.navigateTo(Screen.ItemSearchFragment())
    }

    override fun openHotelReport(type: String) {
        router.navigateTo(Screen.ItemReportFragment(type))
    }

    override fun openGuestSearch() {
        router.navigateTo(Screen.ItemSearchGuestFragment())
    }

    override fun reportGuest(type: String) {
        router.navigateTo(Screen.ItemReportFragment(type))
    }

    override fun openRoomtSearch() {
        router.navigateTo(Screen.ItemSearchRoomFragment())
    }

    override fun reportRoom(s: String) {
        router.navigateTo(Screen.ItemReportFragment(s))
    }
}