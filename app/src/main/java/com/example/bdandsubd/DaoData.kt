package com.example.bdandsubd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bdandsubd.entities.Guest
import com.example.bdandsubd.entities.Hotel
import com.example.bdandsubd.entities.Room
import com.example.bdandsubd.entities.RoomGet

@Dao
interface DaoData {
    @Query("SELECT * FROM Hotel")
    fun getDateFromHotel():List<Hotel>
    @Insert(Hotel::class)
    fun insertDataInHotel(hotel: Hotel)

    @Query("SELECT * FROM Guest")
    fun getDataFromGuest():List<Guest>
    @Insert(Guest::class)
    fun insertDataFromGuest(guest: Guest)

    @Query("SELECT Room.id,Guest.name as 'nameGuest',Hotel.name as 'nameHotel', Room.roomNumber, Room.type, Room.price  FROM Room JOIN Guest ON Room.guest_id=Guest.id JOIN Hotel ON Room.hotel_id=Hotel.id")
    fun getDataFromRoom():List<RoomGet>

    @Insert(Room::class)
    fun insertDtaIntoRoom(room: Room)
}
