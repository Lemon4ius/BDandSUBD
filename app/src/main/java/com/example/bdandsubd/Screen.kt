package com.example.bdandsubd

import com.example.bdandsubd.presenter.GuestListFragment
import com.example.bdandsubd.presenter.ListTablesFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screen {
    fun TabledListFrag()=FragmentScreen{ ListTablesFragment.newInstance()}
    fun ItemsListFrag()=FragmentScreen{ GuestListFragment.newInstance()}
}