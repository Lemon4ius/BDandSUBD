package com.example.bdandsubd.presenter.addPresentor

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentAddHotelBinding
import com.example.bdandsubd.entities.Hotel
import com.example.bdandsubd.entities.getter.HotelGet
import com.example.bdandsubd.presenter.HotelListFragment
import com.example.bdandsubd.presenter.viewmodels.SharedHotelViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddHotelFragment : Fragment() {

    lateinit var binding: FragmentAddHotelBinding
    var starRating: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddHotelBinding.inflate(inflater)
        val check = arguments?.getBoolean(CHECK_CREATE_OT_EDIT)
        if (check!!) {
            setFragmentResultListener(HotelListFragment.FRAGMENT_KEY) { requestKey, bundle ->
                val hotel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getSerializable(
                        HotelListFragment.BUNDLE_KEY,
                        HotelGet::class.java
                    ) as HotelGet
                } else {
                    bundle.getSerializable(HotelListFragment.BUNDLE_KEY) as HotelGet
                }
                binding.hotelName.setText(hotel.name)
                binding.hotelAdress.setText(hotel.address)
                binding.hotelRoom.setText(hotel.roomCount.toString())
                binding.addButton.text = "Изменить"
                binding.ratingBar.rating = hotel.starRating.toFloat()
                editData(hotel)
            }
        } else {
            addingData()
            ratingBarControll()
        }
        return binding.root
    }

    private fun editData(hotel: HotelGet) {
        val viewModel = ViewModelProvider(requireActivity()).get(SharedHotelViewModel::class.java)
        binding.addButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val job =launch(Dispatchers.IO) {
                    DbWorker.newsDao.updateHotel(
                        Hotel(
                            hotel.id,
                            binding.hotelName.text.toString(),
                            binding.hotelAdress.text.toString(),
                            binding.ratingBar.rating.toInt(),
                            binding.hotelRoom.text.toString().toInt()
                        )
                    )
                }
                job.join()
                viewModel.setCallbackValue(true)
                parentFragmentManager.beginTransaction().remove(this@AddHotelFragment).commit()
            }
        }
        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }


    private fun ratingBarControll() {
        binding.ratingBar.onRatingBarChangeListener = object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                starRating = rating.toInt()
            }
        }
    }

    private fun addingData() {
        val viewModel = ViewModelProvider(requireActivity()).get(SharedHotelViewModel::class.java)
        binding.addButton.setOnClickListener {
            val name = binding.hotelName.text.toString()
            val adress = binding.hotelAdress.text.toString()
            val countRoom = binding.hotelRoom.text.toString().toInt()
            viewLifecycleOwner.lifecycleScope.launch {
                val job = launch(Dispatchers.IO) {
                    DbWorker.newsDao.insertDataInHotel(
                        Hotel(
                            null,
                            name,
                            adress,
                            starRating,
                            countRoom
                        )
                    )
                }
                job.join()
                viewModel.setCallbackValue(true)
                parentFragmentManager.beginTransaction().remove(this@AddHotelFragment).commit()
            }

        }
        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    companion object {
        const val CHECK_CREATE_OT_EDIT = "check"

        @JvmStatic
        fun newInstance(create: Boolean) =
            AddHotelFragment().apply {
                arguments = bundleOf(CHECK_CREATE_OT_EDIT to create)
            }
    }
}