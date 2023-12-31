package com.example.bdandsubd.recycle.listeners

import com.example.bdandsubd.entities.getter.GuestGet
import com.example.bdandsubd.entities.getter.HotelGet

interface HotelListener {
    fun editHotel(hotel: HotelGet)
    suspend fun deleteHotel(hotel: HotelGet)
}