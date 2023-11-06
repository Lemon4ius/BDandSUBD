package com.example.bdandsubd.presenter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bdandsubd.MainActivity
import com.example.bdandsubd.R
import com.example.bdandsubd.presenter.viewmodels.SharedViewModel
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentRoomListBinding
import com.example.bdandsubd.entities.getter.RoomGet
import com.example.bdandsubd.navigation.RoomlNavigation
import com.example.bdandsubd.presenter.addPresentor.AddHotelFragment
import com.example.bdandsubd.presenter.addPresentor.AddRoomFragment
import com.example.bdandsubd.presenter.viewmodels.SharedRoomViewModel
import com.example.bdandsubd.recycle.RecycleAdapter
import com.example.bdandsubd.recycle.listeners.RoomListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RoomListFragment : Fragment() {

    lateinit var binding: FragmentRoomListBinding
    lateinit var roomlNavigation: RoomlNavigation
    override fun onAttach(context: Context) {
        super.onAttach(context)
        roomlNavigation = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomListBinding.inflate(inflater)
        viewmodelListener()
        topAppBarNav()
        deleteData()
        binding.addingButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(binding.frameLayoutDialog.id, AddRoomFragment.newInstance(false))
                .commit()
        }
        adapterInit()

        return binding.root
    }

    private fun deleteData() {
        binding.deleteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                adapter.deleteRoom()
            }
        }
        binding.cancelButton.setOnClickListener {
            binding.toolbar.visibility = View.GONE
            adapter.dissableCheckBox()
        }
    }

    private fun topAppBarNav() {
        binding.topAppBar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.trash -> {
                        binding.toolbar.visibility = View.VISIBLE
                        adapter.checkBoxActive()
                    }
                    R.id.search_bar->{
                        roomlNavigation.openRoomtSearch()
                    }

                }
                return true
            }
        })
    }

    private fun viewmodelListener() {
        var data: List<RoomGet>? = null
        val viewModel = ViewModelProvider(requireActivity()).get(SharedRoomViewModel::class.java)
        viewModel.getCallbackLiveData().observe(viewLifecycleOwner) { value ->
            viewLifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    data = DbWorker.newsDao.getDataFromRoom()
                }
                withContext(Dispatchers.Main) {
                    adapter.roomList = data!!
                }
            }
        }
    }


    lateinit var adapter: RecycleAdapter
    private fun adapterInit() {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val roomdata = DbWorker.newsDao.getDataFromRoom()
                adapter = RecycleAdapter(roomdata, 4, object : RoomListener {
                    override fun editRoom(room: RoomGet) {
                        setFragmentResult(
                            FRAGMENT_ROOM_KEY,
                            bundleOf(AddRoomFragment.BUNDLE_ROOM_KEY to room)
                        )
                        parentFragmentManager.beginTransaction()
                            .replace(
                                binding.frameLayoutDialog.id,
                                AddRoomFragment.newInstance(true)
                            )
                            .commit()
                    }

                    override suspend fun deleteGuest(room: RoomGet) {
                        withContext(Dispatchers.IO) {
                            DbWorker.newsDao.deleteRoom(room.id!!)
                            val updateGuest = DbWorker.newsDao.getDataFromRoom()
                            withContext(Dispatchers.Main) {
                                adapter.roomList = updateGuest
                            }
                        }
                    }
                })
                withContext(Dispatchers.Main) {
                    binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
                    binding.recycleView.adapter = adapter
                    binding.recycleView.addItemDecoration(
                        DividerItemDecoration(
                            requireContext(),
                            DividerItemDecoration.VERTICAL
                        )
                    )
                }
            }

        }
    }


    companion object {
        const val FRAGMENT_ROOM_KEY = "roomKey"

        @JvmStatic
        fun newInstance() = RoomListFragment()
    }


}