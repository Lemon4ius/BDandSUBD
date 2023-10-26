package com.example.bdandsubd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bdandsubd.databinding.FragmentItemsListBinding
import com.example.bdandsubd.recycle.RecycleAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GuestListFragment : Fragment() {
   lateinit var binding: FragmentItemsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentItemsListBinding.inflate(inflater)
        CoroutineScope(Dispatchers.IO).launch {
            val date= DbWorker.newsDao.getDataFromRoom()
            
        }
        val adapter = RecycleAdapter(3, object: MyClickListener{
            override fun getGuestClick() {
                TODO("Not yet implemented")
            }

            override fun getRoomClick() {
                TODO("Not yet implemented")
            }

            override fun getHotelClick() {
                TODO("Not yet implemented")
            }
        })

        binding.recycleView.layoutManager=LinearLayoutManager(requireContext())
        binding.recycleView.adapter=adapter
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = GuestListFragment()
    }
}