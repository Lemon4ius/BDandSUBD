package com.example.bdandsubd.navigation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteTable
import androidx.room.Insert
import androidx.room.Query
import com.example.bdandsubd.entities.Guest
import com.example.bdandsubd.entities.getter.GuestGet
import com.example.bdandsubd.entities.Hotel
import com.example.bdandsubd.entities.Room
import com.example.bdandsubd.entities.getter.HotelGet
import com.example.bdandsubd.entities.getter.RoomGet

@Dao
interface DaoData {
    @Query("SELECT * FROM Hotel")
    fun getDateFromHotel():List<HotelGet>
    @Insert(Hotel::class)
    fun insertDataInHotel(hotel: Hotel)
    @Query("DELETE FROM Hotel WHERE id=:id")
    fun deleteHotel(id: Int)

    @Query("SELECT * FROM Guest")
    fun getDataFromGuest():List<GuestGet>
    @Insert(Guest::class)
    fun insertDataFromGuest(guest: Guest)
    @Query("DELETE FROM Guest WHERE id=:id")
    fun deleteGuest(id: Int)

    @Query("SELECT Room.id,Guest.name as 'nameGuest',Hotel.name as 'nameHotel', Room.roomNumber, Room.type, Room.price  FROM Room JOIN Guest ON Room.guest_id=Guest.id JOIN Hotel ON Room.hotel_id=Hotel.id")
    fun getDataFromRoom():List<RoomGet>
    @Insert(Room::class)
    fun insertDtaIntoRoom(room: Room)
    @Query("DELETE FROM Room WHERE id=:id")
    fun deleteRoom(id: Int)

}
