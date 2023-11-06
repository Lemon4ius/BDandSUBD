package com.example.bdandsubd.presenter

import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentSearchBinding
import com.example.bdandsubd.entities.getter.GuestGet
import com.example.bdandsubd.entities.getter.HotelGet
import com.example.bdandsubd.entities.getter.RoomGet
import com.example.bdandsubd.recycle.RecycleAdapter
import com.example.bdandsubd.recycle.WrapContentLinearLayoutManager
import com.example.bdandsubd.recycle.listeners.HotelListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    var args: Bundle? = null
    lateinit var adapter: RecycleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)

        args = arguments
        var viewType=-1
        if (args?.containsKey(BUNDLE_INSTANCE_SEARCH_HOTEL_KEY) == true){
            viewType=RecycleAdapter.HOTEL_VIEW
        }
        if (args?.containsKey(BUNDLE_INSTANCE_SEARCH_ROOM_KEY2) == true){viewType=RecycleAdapter.Room_VIEW}
        if (args?.containsKey(BUNDLE_INSTANCE_SEARCH_GUEST_KEY3) == true){viewType=RecycleAdapter.GUEST_VIEW}
        adapter = RecycleAdapter(emptyList(), viewType, object : HotelListener {
            override fun editHotel(hotel: HotelGet) {
                TODO("Not yet implemented")
            }

            override suspend fun deleteHotel(hotel: HotelGet) {
                TODO("Not yet implemented")
            }
        })
        binding.recycleView.layoutManager =
            WrapContentLinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recycleView.adapter = adapter
        searViewControll()
        return binding.root
    }

    private fun searViewControll() {
        if (args?.containsKey(BUNDLE_INSTANCE_SEARCH_ROOM_KEY2) == true) {
            binding.searchView.inputType = InputType.TYPE_CLASS_NUMBER
        }
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("Searhcer", newText!!)

                if (args?.containsKey(BUNDLE_INSTANCE_SEARCH_HOTEL_KEY) == true) {
                    if (newText.isNotEmpty()) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                val data = DbWorker.newsDao.searchHotel(newText)
                                withContext(Dispatchers.Main) {
                                    adapter.hotelList = data
                                }
                            }
                        }
                    }

                }
                if (args?.containsKey(BUNDLE_INSTANCE_SEARCH_ROOM_KEY2) == true) {
                    if (newText.isNotEmpty()) {
                        viewLifecycleOwner.lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                val data = DbWorker.newsDao.searchRoom(newText.toInt())
                                withContext(Dispatchers.Main) {

                                    adapter.roomList = data
                                }
                            }
                        }
                    }
                }
                if (args?.containsKey(BUNDLE_INSTANCE_SEARCH_GUEST_KEY3) == true)
                {
                    viewLifecycleOwner.lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            val data = DbWorker.newsDao.searchGuest(newText)
                            withContext(Dispatchers.Main) {
                                adapter.guestList = data
                            }
                        }
                    }
                }

                return true
            }
        })
    }

    companion object {
        const val BUNDLE_INSTANCE_SEARCH_HOTEL_KEY = "bindleInsstacekey"
        const val BUNDLE_INSTANCE_SEARCH_ROOM_KEY2 = "bindleInsstacekey2"
        const val BUNDLE_INSTANCE_SEARCH_GUEST_KEY3 = "bindleInsstacekey3"

        @JvmStatic
        fun newInstance(hotel: Int) = SearchFragment().apply {
            arguments = bundleOf(BUNDLE_INSTANCE_SEARCH_HOTEL_KEY to hotel)
        }

        @JvmStatic
        fun newInstance(roomGet: Boolean) = SearchFragment().apply {
            arguments = bundleOf(BUNDLE_INSTANCE_SEARCH_ROOM_KEY2 to roomGet)
        }

        @JvmStatic
        fun newInstance(guestGet: String) = SearchFragment().apply {
            arguments = bundleOf(BUNDLE_INSTANCE_SEARCH_GUEST_KEY3 to guestGet)
        }
    }
}