package com.example.bdandsubd.recycle.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bdandsubd.databinding.ListItemHotelBinding
import com.example.bdandsubd.entities.getter.HotelGet

class HotelHolder(val binding: ListItemHotelBinding):RecyclerView.ViewHolder(binding.root) {

    fun bind(hotel: HotelGet, checkActive: Boolean){
        if (checkActive) {
            binding.checkBox.visibility = View.VISIBLE
        } else {
            binding.checkBox.visibility = View.GONE
            binding.checkBox.isChecked = false
        }
        with(binding){
            nameHotel.text=hotel.name
            adress.text=hotel.address
            countRoom.text ="Количество комнат: " +hotel.roomCount.toString()
            startNum.text=hotel.starRating.toString()
        }
    }
}