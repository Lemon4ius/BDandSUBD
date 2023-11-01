package com.example.bdandsubd.recycle.diffUtills

import androidx.recyclerview.widget.DiffUtil
import com.example.bdandsubd.entities.getter.RoomGet

class RoomDiffUtill(
    private val oldList: List<RoomGet>,
    private val newList: List<RoomGet>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int =oldList.size

    override fun getNewListSize(): Int=newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldValue =oldList.get(oldItemPosition)
        val newValue =newList.get(newItemPosition)
        return oldValue.id==newValue.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldValue =oldList[oldItemPosition]
        val newValue =newList[newItemPosition]
        return oldValue==newValue
    }
}