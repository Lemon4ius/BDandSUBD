package com.example.bdandsubd.presenter.addPresentor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bdandsubd.R
import com.example.bdandsubd.SharedViewModel
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentAddGuest2Binding
import com.example.bdandsubd.entities.Guest
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class AddGuestFragment : Fragment() {

    lateinit var binding: FragmentAddGuest2Binding
    var from: String=""
    var to: String=""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentAddGuest2Binding.inflate(inflater)
        val viewModel= ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
        binding.addButton.setOnClickListener{
            val name= binding.nameEdit.text.toString()
            val email= binding.emailEdit.text.toString()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dateFrom = LocalDate.parse(from, formatter)
            val dateTo=LocalDate.parse(to,formatter)
            viewLifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO){
                    DbWorker.newsDao.insertDataFromGuest(Guest(null,name,email,dateFrom,dateTo ))
                }
            }
            viewModel.setCallbackValue(true)
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
        binding.relativeLayout.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select date")
                .setTheme(R.style.DatePicker)
                .build()

            datePicker.addOnPositiveButtonClickListener {
                from = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it.first))
                to = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it.second))

            }
            datePicker.show(requireActivity().supportFragmentManager, "datePicker")
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = AddGuestFragment()
    }
}