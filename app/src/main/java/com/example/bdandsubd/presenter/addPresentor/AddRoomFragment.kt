package com.example.bdandsubd.presenter.addPresentor

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentAddGuestBinding
import com.example.bdandsubd.entities.getter.GuestGet
import com.example.bdandsubd.entities.Room
import com.example.bdandsubd.entities.getter.HotelGet
import com.example.bdandsubd.entities.getter.RoomGet
import com.example.bdandsubd.presenter.RoomListFragment
import com.example.bdandsubd.presenter.spinerAdapter.GuestSpinerAdapter
import com.example.bdandsubd.presenter.spinerAdapter.HotelSpinerAdapter
import com.example.bdandsubd.presenter.viewmodels.SharedRoomViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddRoomFragment : Fragment() {
    lateinit var binding: FragmentAddGuestBinding
    lateinit var viewModel: SharedRoomViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddGuestBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity()).get(SharedRoomViewModel::class.java)
        val check = arguments?.getBoolean(BUNDLE_ROOM_INSTANCE_KEY)
        if (check!!) {
            setFragmentResultListener(RoomListFragment.FRAGMENT_ROOM_KEY){requestKey, bundle ->
                val room = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getSerializable(
                        BUNDLE_ROOM_KEY,
                        RoomGet::class.java
                    ) as RoomGet
                } else {
                    bundle.getSerializable(
                        BUNDLE_ROOM_KEY
                    ) as RoomGet
                }

                binding.numberOfRoom.setText(room.roomNumber.toString())
                binding.priceRoom.setText(room.price.toString())
                //initSpinersEdit(room)
                initSpiners()
                editControll(room)
            }
        } else {
            initSpiners()
            addingBtnListener()
        }

        return binding.root
    }

    private fun editControll(room: RoomGet) {
        binding.addButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val job = launch(Dispatchers.IO) {
                    DbWorker.newsDao.updateRoom(
                        Room(
                            room.id,
                            selectedhotel,
                            idGuest = selectedguest,
                            binding.numberOfRoom.text.toString().toInt(),
                            type = selectedtype,
                            binding.priceRoom.text.toString().toDouble()
                        )
                    )
                }
                job.join()
                viewModel.setCallbackValue(true)
                parentFragmentManager.beginTransaction().remove(this@AddRoomFragment).commit()
            }
        }
        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    private fun initSpinersEdit(room: RoomGet) {
        val typeRoom =
            listOf<String>("Стандартный", "Улучшенный", "Люкс", "Презеденский", "Двухэтажный")
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val guestArray = DbWorker.newsDao.getDataFromGuest()
                val guestAdapter = GuestSpinerAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    guestArray
                )
                guestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                val hotelArray = DbWorker.newsDao.getDateFromHotel()
                val hotelAdapter = HotelSpinerAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    hotelArray
                )
                hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                val typeRoomArray = DbWorker.newsDao.getDateFromHotel()
                val typeRoomAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeRoom)
                hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                withContext(Dispatchers.Main) {
                    binding.guestList.adapter = guestAdapter
                    binding.hotelList.adapter = hotelAdapter
                    binding.typeOfRoom.adapter = typeRoomAdapter
                }

                listenerSpinerGuest()
                listenerSpinerHotel()
                listenerSpinerTypeOfRoom()
//                setDefaultGuest(guestArray,room,guestAdapter)
//                setDefaultHotel(hotelArray,room,hotelAdapter)

            }
        }

    }

    private fun setDefaultHotel(
        hotelArray: List<HotelGet>,
        room: RoomGet,
        hotelAdapter1: HotelSpinerAdapter
    ) {
        var hotelFind:HotelGet?=null
        val guestName=hotelArray.forEach{
            if (it.name==room.nameGuest){
                hotelFind=it
            }
        }
        val guestSelection=hotelAdapter1.getPosition(hotelFind)
        binding.guestList.setSelection(guestSelection)
    }

    private fun setDefaultGuest(
        guestArray: List<GuestGet>,
        room: RoomGet,
        guestAdapter: GuestSpinerAdapter
    ) {
        var guestFind: GuestGet?=null
        guestArray.forEach{
            if (it.name==room.nameGuest){
                guestFind=it
            }
        }
        val guestSelection=guestAdapter.getPosition(guestFind)
        binding.guestList.setSelection(guestSelection)
    }

    private fun addingBtnListener() {
        binding.addButton.setOnClickListener {
            val numberOfRoom = binding.numberOfRoom.text.toString().toInt()
            val price = binding.priceRoom.text.toString().toInt()
            viewLifecycleOwner.lifecycleScope.launch {
                val job = launch(Dispatchers.IO) {
                    DbWorker.newsDao.insertDtaIntoRoom(
                        Room(
                            null,
                            selectedhotel,
                            idGuest = selectedguest,
                            numberOfRoom,
                            type = selectedtype,
                            price.toDouble()
                        )
                    )
                }
                job.join()
                viewModel.setCallbackValue(true)
                parentFragmentManager.beginTransaction().remove(this@AddRoomFragment).commit()
            }
        }
        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

    }

    private fun initSpiners() {
        val typeRoom =
            listOf<String>("Стандартный", "Улучшенный", "Люкс", "Презеденский", "Двухэтажный")
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val guestArray = DbWorker.newsDao.getDataFromGuest()
                val guestAdapter = GuestSpinerAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    guestArray
                )
                guestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                val hotelArray = DbWorker.newsDao.getDateFromHotel()
                val hotelAdapter = HotelSpinerAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    hotelArray
                )
                hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                val typeRoomArray = DbWorker.newsDao.getDateFromHotel()
                val typeRoomAdapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, typeRoom)
                hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)



                withContext(Dispatchers.Main) {
                    binding.guestList.adapter = guestAdapter
                    binding.hotelList.adapter = hotelAdapter
                    binding.typeOfRoom.adapter = typeRoomAdapter
                }
                listenerSpinerGuest()
                listenerSpinerHotel()
                listenerSpinerTypeOfRoom()

            }
        }


    }

    var selectedguest: Int = -1
    var selectedtype: String = ""
    var selectedhotel: Int = -1
    private fun listenerSpinerTypeOfRoom() {
        binding.typeOfRoom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedType = binding.typeOfRoom.selectedItem as String
                selectedtype = selectedType
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun listenerSpinerHotel() {
        binding.hotelList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedHotel = binding.hotelList.selectedItem as HotelGet
                selectedhotel = selectedHotel.id!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }


    private fun listenerSpinerGuest() {
        binding.guestList.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedGuest = binding.guestList.selectedItem as GuestGet
                selectedguest = selectedGuest.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    companion object {
        const val BUNDLE_ROOM_KEY = "bundleRoom"
        const val BUNDLE_ROOM_INSTANCE_KEY = "bundleinstance"

        @JvmStatic
        fun newInstance(check: Boolean) = AddRoomFragment().apply {
            arguments = bundleOf(BUNDLE_ROOM_INSTANCE_KEY to check)
        }
    }
}