package com.example.bdandsubd.presenter.addPresentor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bdandsubd.R
import com.example.bdandsubd.SharedViewModel
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentAddHotelBinding
import com.example.bdandsubd.entities.Hotel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddHotelFragment : Fragment() {

    lateinit var binding:FragmentAddHotelBinding
    var starRating:Int=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentAddHotelBinding.inflate(inflater)

        binding.ratingBar.onRatingBarChangeListener=object : RatingBar.OnRatingBarChangeListener{
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                starRating=rating.toInt()
            }
        }
        val viewModel= ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        binding.addButton.setOnClickListener {
            val name=binding.hotelName.text.toString()
            val adress=binding.hotelAdress.text.toString()
            val countRoom=binding.hotelRoom.text.toString().toInt()
            viewLifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    DbWorker.newsDao.insertDataInHotel(Hotel(null,name,adress,starRating,countRoom))
                }
            }
            viewModel.setCallbackValue(true)
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        return binding.root
    }

    companion object {


        @JvmStatic
        fun newInstance() =
            AddHotelFragment()
    }
}