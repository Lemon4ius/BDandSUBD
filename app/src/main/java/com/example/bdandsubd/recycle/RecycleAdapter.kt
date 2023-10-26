package com.example.bdandsubd.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bdandsubd.MyClickListener
import com.example.bdandsubd.databinding.ListItemGuestBinding
import com.example.bdandsubd.databinding.ListItemRoomBinding
import com.example.bdandsubd.databinding.TableItemBinding
import com.example.bdandsubd.entities.Guest
import com.example.bdandsubd.entities.RoomGet
import com.example.bdandsubd.recycle.holders.GuestHolder
import com.example.bdandsubd.recycle.holders.RoomHolder
import com.example.bdandsubd.recycle.holders.TableHolder

class RecycleAdapter () :RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener{
    companion object{
        const val TABLE_VIEW = 1
        const val ITEM_VIEW = 2
        const val GUEST_VIEW = 3
        const val Room_VIEW = 4
    }
    var viewtype:Int=0
    lateinit var tablesList: List<String>
    lateinit var myclickListener: MyClickListener
    lateinit var guestList: List<Guest>
    lateinit var roomList: List<RoomGet>
    constructor(tables:List<String>, clickListener: MyClickListener,  viewType: Int) : this() {
        tablesList=tables
        myclickListener=clickListener
        this.viewtype= viewType
    }
    constructor(guestes:List<Guest>,viewTypeGet: Int ) : this() {
        viewtype=viewTypeGet
        guestList=guestes
    }

    constructor(room:List<RoomGet>,viewTypeGet: Int) : this() {
        roomList=room
        viewtype=viewTypeGet
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val infalter= LayoutInflater.from(parent.context)
        when(viewType){
            TABLE_VIEW ->{
                val binding =
                    TableHolder(TableItemBinding.inflate(infalter, parent, false))
                binding.itemView.setOnClickListener(this)
                return binding
            }
//            ITEM_VIEW->{
//                return null
//            }
            GUEST_VIEW->{
                val binding=GuestHolder(ListItemGuestBinding.inflate(infalter,parent,false))
                binding.itemView.setOnClickListener(this)
                return binding
            }
            Room_VIEW->{
                val binding=RoomHolder(ListItemRoomBinding.inflate(infalter,parent,false))
                binding.itemView.setOnClickListener(this)
                return binding
            }
            else->throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            1->{
                val table = tablesList.get(position)
                holder.itemView.tag=table
                (holder as TableHolder).bind(table)
            }
            2->{

            }
            3->{
                val guests=guestList.get(position)
                holder.itemView.tag=guests
                (holder as GuestHolder).bind(guests)
            }
            4->{
                val room =roomList.get(position)
                holder.itemView.tag=room
                (holder as RoomHolder).bind(room)
            }
        }
    }

    override fun getItemCount(): Int {
        when(viewtype){
            1->{
                return tablesList.size
            }
            2->{
                return 10
            }
            3->{return guestList.size}
            4->{
                return roomList.size
            }
        }
        return 0
    }

    override fun getItemViewType(position: Int): Int {
        return viewtype
    }
    override fun onClick(v: View?) {

        when(viewtype){
         1->{
             val table= v?.tag as String
             when (table) {
                 "Отели" -> myclickListener.getHotelClick()
                 "Комнаты" -> myclickListener.getRoomClick()
                 "Гости" -> myclickListener.getGuestClick()
             }
         }
        }
    }


}