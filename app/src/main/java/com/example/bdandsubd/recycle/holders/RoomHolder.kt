package com.example.bdandsubd.recycle.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.bdandsubd.databinding.ListItemRoomBinding
import com.example.bdandsubd.entities.RoomGet

class RoomHolder(val binding: ListItemRoomBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(room:RoomGet){
        binding.nameHotel.text = room.nameHotel
        binding.price.text = room.price.toString()
        binding.nuberRoom.text = room.roomNumber.toString()
        binding.roomType.text = room.type
    }
}