package com.example.bdandsubd

import com.example.bdandsubd.presenter.BarChartFragment
import com.example.bdandsubd.presenter.ExportWordFragment
import com.example.bdandsubd.presenter.PieDiagramsFragment
import com.example.bdandsubd.presenter.GuestListFragment
import com.example.bdandsubd.presenter.HotelListFragment
import com.example.bdandsubd.presenter.ListTablesFragment
import com.example.bdandsubd.presenter.ReportFragment
import com.example.bdandsubd.presenter.RoomListFragment
import com.example.bdandsubd.presenter.SearchFragment
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screen {
    fun TabledListFrag()=FragmentScreen{ ListTablesFragment.newInstance()}
    fun ItemsListFrag()=FragmentScreen{ GuestListFragment.newInstance()}
    fun ItemRoomListFrag()=FragmentScreen{RoomListFragment.newInstance()}
    fun ItemHotelFrag()= FragmentScreen{HotelListFragment.newInstance()}
    fun ItemSearchFragment() =FragmentScreen{SearchFragment.newInstance(1)}
    fun ItemSearchRoomFragment() =FragmentScreen{SearchFragment.newInstance(false)}
    fun ItemSearchGuestFragment() =FragmentScreen{SearchFragment.newInstance("")}

    fun ItemDiagramFragment()= FragmentScreen{PieDiagramsFragment.newInstance()}
    fun ItemDiagramBarFragment()= FragmentScreen{BarChartFragment.newInstance()}
    fun ItemExporFragment()=FragmentScreen{ExportWordFragment.newInstance()}
    fun ItemReportFragment(type:String)=FragmentScreen{ReportFragment.newInstance(type)}
}