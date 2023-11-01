package com.example.bdandsubd

import com.example.bdandsubd.presenter.GuestListFragment
import com.example.bdandsubd.presenter.HotelListFragment
import com.example.bdandsubd.presenter.ListTablesFragment
import com.example.bdandsubd.presenter.RoomListFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screen {
    fun TabledListFrag()=FragmentScreen{ ListTablesFragment.newInstance()}
    fun ItemsListFrag()=FragmentScreen{ GuestListFragment.newInstance()}
    fun ItemRoomListFrag()=FragmentScreen{RoomListFragment.newInstance()}
    fun ItemHotelFrag()= FragmentScreen{HotelListFragment.newInstance()}
}