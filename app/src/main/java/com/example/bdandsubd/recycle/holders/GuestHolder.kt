package com.example.bdandsubd.recycle.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.bdandsubd.databinding.ListItemGuestBinding
import com.example.bdandsubd.entities.Guest
import java.time.format.DateTimeFormatter

class GuestHolder(val binding: ListItemGuestBinding):RecyclerView.ViewHolder(binding.root) {
    fun bind(guest:Guest){
        binding.name.text=guest.name
        binding.email.text=guest.email
        binding.dateIn.text="Дата заселения: "+guest.checkInDate.format(DateTimeFormatter.ofPattern("dd MM yyyy"))
        binding.dateOut.text="Дата выселения: "+guest.checkInDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
}