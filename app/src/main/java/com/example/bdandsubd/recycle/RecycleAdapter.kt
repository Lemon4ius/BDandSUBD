package com.example.bdandsubd.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bdandsubd.recycle.listeners.GuestListener
import com.example.bdandsubd.recycle.listeners.MyClickListener
import com.example.bdandsubd.databinding.ListItemGuestBinding
import com.example.bdandsubd.databinding.ListItemHotelBinding
import com.example.bdandsubd.databinding.ListItemRoomBinding
import com.example.bdandsubd.databinding.TableItemBinding
import com.example.bdandsubd.entities.getter.GuestGet
import com.example.bdandsubd.entities.getter.HotelGet
import com.example.bdandsubd.entities.getter.RoomGet
import com.example.bdandsubd.recycle.diffUtills.GuestDiffUtill
import com.example.bdandsubd.recycle.diffUtills.HotelDiffUtill
import com.example.bdandsubd.recycle.diffUtills.RoomDiffUtill
import com.example.bdandsubd.recycle.holders.GuestHolder
import com.example.bdandsubd.recycle.holders.HotelHolder
import com.example.bdandsubd.recycle.holders.RoomHolder
import com.example.bdandsubd.recycle.holders.TableHolder
import com.example.bdandsubd.recycle.listeners.HotelListener
import com.example.bdandsubd.recycle.listeners.RoomListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecycleAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener {
    companion object {
        const val TABLE_VIEW = 1
        const val GUEST_VIEW = 3
        const val Room_VIEW = 4
        const val HOTEL_VIEW = 5
    }

    var viewtype: Int = 0
    lateinit var tablesList: List<String>
    lateinit var myclickListener: MyClickListener
    var guestList: List<GuestGet> = emptyList()
        set(value) {
            val diffCallBack = GuestDiffUtill(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }
    var roomList: List<RoomGet> =emptyList()
       set(value) {
           val diffCallBack = RoomDiffUtill(field, value)
           val diffResult = DiffUtil.calculateDiff(diffCallBack)
           field = value
           diffResult.dispatchUpdatesTo(this)
        }


    var hotelList: List<HotelGet> = emptyList()
        set(value) {
            val diffCallBack = HotelDiffUtill(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallBack)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    var guestCheckActive = ArrayList<GuestGet>()
    var roomCheckActive = ArrayList<RoomGet>()
    var hotelCheckActive = ArrayList<HotelGet>()

    constructor(tables: List<String>, clickListener: MyClickListener, viewType: Int) : this() {
        tablesList = tables
        myclickListener = clickListener
        this.viewtype = viewType
    }

    lateinit var guestListener: GuestListener
    lateinit var roomListener: RoomListener
    lateinit var hotelListener: HotelListener

    constructor(guestes: List<GuestGet>, viewTypeGet: Int, guestListener: GuestListener) : this() {
        viewtype = viewTypeGet
        guestList = guestes
        this.guestListener = guestListener
    }


    constructor(room: List<RoomGet>, viewTypeGet: Int,roomListener: RoomListener) : this() {
        roomList = room
        viewtype = viewTypeGet
        this.roomListener=roomListener
    }

    constructor(
        hotel: List<HotelGet>,
        viewType: Int,
        hotelListener: HotelListener
    ) : this() {
        hotelList = hotel
        viewtype = viewType
        this.hotelListener=hotelListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val infalter = LayoutInflater.from(parent.context)
        when (viewType) {
            TABLE_VIEW -> {
                val binding =
                    TableHolder(TableItemBinding.inflate(infalter, parent, false))
                binding.itemView.setOnClickListener(this)
                return binding
            }

            GUEST_VIEW -> {
                val binding = GuestHolder(ListItemGuestBinding.inflate(infalter, parent, false))
                binding.itemView.setOnClickListener(this)
                return binding
            }

            Room_VIEW -> {
                val binding = RoomHolder(ListItemRoomBinding.inflate(infalter, parent, false))
                binding.itemView.setOnClickListener(this)
                return binding
            }

            HOTEL_VIEW -> {
                val binding = HotelHolder(ListItemHotelBinding.inflate(infalter, parent, false))
                binding.itemView.setOnClickListener(this)
                return binding
            }

            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            1 -> {
                val table = tablesList.get(position)
                holder.itemView.tag = table
                (holder as TableHolder).bind(table)
            }

            2 -> {

            }

            3 -> {
                val guests = guestList.get(position)
                holder.itemView.tag = guests
                val binding = (holder as GuestHolder)
                binding.bind(guests, checkActive)
                with(binding.binding) {
                    checkBox.isChecked = guests.isChecked == true
                    checkBox.setOnClickListener {
                        if (checkBox.isChecked) {
                            guests.isChecked = true
                            guestCheckActive.add(guests)
                        } else {
                            guests.isChecked = false
                            guestCheckActive.remove(guests)
                        }
                    }
                }
            }

            4 -> {
                val room = roomList.get(position)
                holder.itemView.tag = room
                val binding =(holder as RoomHolder)
                binding.bind(room,checkActive)
                with(binding.binding) {
                    checkBox.isChecked = room.isChecked == true
                    checkBox.setOnClickListener {
                        if (checkBox.isChecked) {
                            room.isChecked = true
                            roomCheckActive.add(room)
                        } else {
                            room.isChecked = false
                            roomCheckActive.remove(room)
                        }
                    }
                }
            }

            5 -> {
                val hotel = hotelList.get(position)
                holder.itemView.tag = hotel
                val binding = (holder as HotelHolder)
                binding.bind(hotel,checkActive)
                with(binding.binding) {
                    checkBox.isChecked = hotel.isChecked == true
                    checkBox.setOnClickListener {
                        if (checkBox.isChecked) {
                            hotel.isChecked = true
                            hotelCheckActive.add(hotel)
                        } else {
                            hotel.isChecked = false
                            hotelCheckActive.remove(hotel)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        when (viewtype) {
            1 -> {
                return tablesList.size
            }
            3 -> {
                return guestList.size
            }

            4 -> {
                return roomList.size
            }

            5 -> {
                return hotelList.size
            }
        }
        return 0
    }

    override fun getItemViewType(position: Int): Int {
        return viewtype
    }

    override fun onClick(v: View?) {

        when (viewtype) {
            1 -> {
                val table = v?.tag as String
                when (table) {
                    "Отели" -> myclickListener.getHotelClick()
                    "Комнаты" -> myclickListener.getRoomClick()
                    "Гости" -> myclickListener.getGuestClick()
                }
            }

            3 -> {
                val guest=v?.tag as GuestGet
                guestListener.editGuest(guest)
            }

            4 -> {

            }

            5 -> {
                val hotel=v?.tag as HotelGet
                hotelListener.editHotel(hotel)
            }
        }
    }

    var checkActive = false
    fun checkBoxActive() {
        checkActive = true
        notifyDataSetChanged()
    }

    fun dissableCheckBox() {
        checkActive = false
        notifyDataSetChanged()
    }

    suspend fun deleteGuest() {
        withContext(Dispatchers.IO) {
            guestCheckActive.forEach { guestListener.deleteGuest(it) }
        }
    }

    suspend fun deleteRoom(){
        withContext(Dispatchers.IO){
            roomCheckActive.forEach { roomListener.deleteGuest(it) }
        }
    }
    suspend fun deleteHotel(){
        withContext(Dispatchers.IO){
            hotelCheckActive.forEach { hotelListener.deleteHotel(it) }
        }
    }
}