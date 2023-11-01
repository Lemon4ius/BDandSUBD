package com.example.bdandsubd.recycle.listeners

import com.example.bdandsubd.entities.getter.GuestGet

interface GuestListener {
    suspend fun deleteGuest(guest: GuestGet)
}