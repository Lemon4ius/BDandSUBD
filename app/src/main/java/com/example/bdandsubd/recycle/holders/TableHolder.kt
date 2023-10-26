package com.example.bdandsubd.recycle.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.bdandsubd.R
import com.example.bdandsubd.databinding.TableItemBinding

class TableHolder(val binding:TableItemBinding):RecyclerView.ViewHolder(binding.root) {

    fun bind(table: String){
        when(table){
            "Гости"->{
                binding.imageView.setImageResource(R.drawable.baseline_people_24)
                binding.nameTable.text=table
            }
            "Комнаты"->{
                binding.imageView.setImageResource(R.drawable.bed_double_svgrepo_com__1_)
                binding.nameTable.text=table
            }
            "Отели"->{
                binding.imageView.setImageResource(R.drawable.baseline_hotel_class_24)
                binding.nameTable.text=table
            }
        }
    }
}
