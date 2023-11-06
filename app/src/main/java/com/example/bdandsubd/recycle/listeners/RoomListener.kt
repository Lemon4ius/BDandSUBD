package com.example.bdandsubd.recycle.listeners

import com.example.bdandsubd.entities.getter.GuestGet
import com.example.bdandsubd.entities.getter.HotelGet
import com.example.bdandsubd.entities.getter.RoomGet

interface RoomListener {
    fun editRoom(room: RoomGet)
    suspend fun deleteGuest(room: RoomGet)
}