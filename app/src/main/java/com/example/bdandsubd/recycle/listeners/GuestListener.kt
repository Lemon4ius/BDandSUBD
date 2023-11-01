package com.example.bdandsubd.recycle.listeners

import com.example.bdandsubd.entities.getter.GuestGet

interface GuestListener {
    fun editGuest(guest: GuestGet)
    suspend fun deleteGuest(guest: GuestGet)
}