package com.example.bdandsubd.recycle.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bdandsubd.databinding.ListItemRoomBinding
import com.example.bdandsubd.entities.getter.RoomGet

class RoomHolder(val binding: ListItemRoomBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(room: RoomGet, checkActive: Boolean){
        if (checkActive) {
            binding.checkBox.visibility = View.VISIBLE
        } else {
            binding.checkBox.visibility = View.GONE
            binding.checkBox.isChecked = false
        }
        binding.nameHotel.text = room.nameHotel
        binding.price.text = room.price.toString()+" BYN"
        binding.nuberRoom.text ="№ "+ room.roomNumber.toString()
        binding.roomType.text = room.type
        binding.guestInRoom.text="Прожживает "+room.nameGuest
    }
}