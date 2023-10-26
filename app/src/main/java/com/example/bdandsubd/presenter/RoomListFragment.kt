package com.example.bdandsubd.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bdandsubd.DbWorker
import com.example.bdandsubd.MyClickListener
import com.example.bdandsubd.R
import com.example.bdandsubd.databinding.FragmentRoomListBinding
import com.example.bdandsubd.recycle.RecycleAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RoomListFragment : Fragment() {

    lateinit var binding: FragmentRoomListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentRoomListBinding.inflate(inflater)
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO){
                val roomdata= DbWorker.newsDao.getDataFromRoom()
                val adapter= RecycleAdapter(roomdata, 4)
                this.launch {
                    binding.recycleView.layoutManager=LinearLayoutManager(requireContext())
                    binding.recycleView.adapter=adapter
                }
            }

        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = RoomListFragment()
    }
}