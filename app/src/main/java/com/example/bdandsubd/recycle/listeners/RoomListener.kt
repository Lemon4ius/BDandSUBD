package com.example.bdandsubd.recycle.listeners

import com.example.bdandsubd.entities.getter.GuestGet
import com.example.bdandsubd.entities.getter.RoomGet

interface RoomListener {
    suspend fun deleteGuest(room: RoomGet)
}