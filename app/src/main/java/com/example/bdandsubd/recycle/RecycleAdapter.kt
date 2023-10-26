package com.example.bdandsubd.recycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bdandsubd.MyClickListener
import com.example.bdandsubd.databinding.ListItemGuestBinding
import com.example.bdandsubd.databinding.TableItemBinding
import com.example.bdandsubd.recycle.holders.GuestHolder
import com.example.bdandsubd.recycle.holders.TableHolder

class RecycleAdapter constructor(val clickListener: MyClickListener) :RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener{
    companion object{
        const val TABLE_VIEW = 1
        const val ITEM_VIEW = 2
        const val GUEST_VIEW = 3
    }
    var viewtype:Int=0
    lateinit var tablesList: List<String>
    lateinit var myclickListener: MyClickListener
    constructor(tables:List<String>, clickListener: MyClickListener,  viewType: Int) : this(clickListener) {
        tablesList=tables
        myclickListener=clickListener
        this.viewtype= viewType
    }
    constructor(viewTypeGet: Int, myClickListener: MyClickListener) : this(myClickListener) {
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
                val tableHolder= holder as TableHolder
                tableHolder.bind(table)
            }
            2->{

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
            3->{return 10}
        }
        return 0
    }

    override fun getItemViewType(position: Int): Int {
        return viewtype
    }
    override fun onClick(v: View?) {
        val table= v?.tag as String

        when (table){
            "Отели"->myclickListener.getHotelClick()
            "Комнаты"->myclickListener.getRoomClick()
            "Гости"->myclickListener.getGuestClick()
        }
    }


}