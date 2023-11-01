package com.example.bdandsubd.recycle.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bdandsubd.databinding.ListItemGuestBinding
import com.example.bdandsubd.entities.getter.GuestGet
import java.time.format.DateTimeFormatter

class GuestHolder(val binding: ListItemGuestBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(guest: GuestGet, checkActive: Boolean) {
        if (checkActive) {
            binding.checkBox.visibility = View.VISIBLE
        } else {
            binding.checkBox.visibility = View.GONE
            binding.checkBox.isChecked = false
        }
        binding.name.text = guest.name
        binding.email.text = guest.email
        binding.dateIn.text =
            "Дата заселения: " + guest.checkInDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        binding.dateOut.text =
            "Дата выселения: " + guest.checkOutDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

    }
}