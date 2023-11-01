package com.example.bdandsubd.presenter.addPresentor

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.bdandsubd.R
import com.example.bdandsubd.presenter.viewmodels.SharedViewModel
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentAddGuest2Binding
import com.example.bdandsubd.entities.Guest
import com.example.bdandsubd.entities.getter.GuestGet
import com.example.bdandsubd.presenter.GuestListFragment
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AddGuestFragment : Fragment() {

    lateinit var binding: FragmentAddGuest2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddGuest2Binding.inflate(inflater)

        val check=arguments?.getBoolean(BUNDLE_KEY_2)
        if (check!!){
            setFragmentResultListener(FRAGMENT_KEY_2){requestKey, bundle ->
                val guest= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getSerializable(GuestListFragment.BUNDLE_RESULT_KEY, GuestGet::class.java) as GuestGet
                } else {
                    bundle.getSerializable(GuestListFragment.BUNDLE_RESULT_KEY)as GuestGet
                }
                binding.nameEdit.setText(guest.name)
                binding.emailEdit.setText(guest.email)
                binding.addButton.text="Изменить"
                datePickerEdit(guest)
                editController(guest)
            }
        }
        else {
            addingControll()
            datePickerControll()
        }
        return binding.root
    }
    var from=Date()
    var to=Date()
    var fromAdd=""
    var toAdd=""
    private fun editController(guest: GuestGet) {
        val from1=SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(from)
        val to1=SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(to)
        val viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateFrom = LocalDate.parse(from1, formatter)
        val dateTo = LocalDate.parse(to1, formatter)
        binding.addButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val job = launch(Dispatchers.IO) {
                    DbWorker.newsDao.updateGuest(
                        Guest(
                            guest.id,
                            binding.nameEdit.text.toString(),
                            binding.emailEdit.text.toString(),
                            dateFrom,
                            dateTo
                        )
                    )
                }
                job.join()
                viewModel.setCallbackValue(true)
                parentFragmentManager.beginTransaction().remove(this@AddGuestFragment).commit()
            }
        }
        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this@AddGuestFragment).commit()
        }
    }

    private fun datePickerEdit(guest: GuestGet) {
        val fromLocalDate = guest.checkInDate.plusDays(1)
        val toLocalDate = guest.checkOutDate.plusDays(1)

        val fromInstant = fromLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
        val toInstant = toLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant()

        from = Date.from(fromInstant)
        to = Date.from(toInstant)

        binding.relativeLayout.setOnClickListener {
            val builder = MaterialDatePicker.Builder.dateRangePicker()

            builder.setSelection(Pair(from?.time, to?.time))

            val datePicker = builder
                .setTheme(R.style.DatePicker)
                .build()


            datePicker.addOnPositiveButtonClickListener {

                from = Date(it.first)
                to = Date(it.second)
            }

            datePicker.show(requireActivity().supportFragmentManager, "datePicker")
        }

    }

    private fun datePickerControll() {

        binding.relativeLayout.setOnClickListener {
            val bulder=MaterialDatePicker.Builder.dateRangePicker()
            val datePicker = bulder
                .setTitleText("Select date")
                .setTheme(R.style.DatePicker)
                .build()
            datePicker.addOnPositiveButtonClickListener {
                fromAdd = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it.first))
                toAdd = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(it.second))
            }

            datePicker.show(requireActivity().supportFragmentManager, "datePicker")
        }
    }

    private fun addingControll() {

        val viewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        binding.cancelButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
        binding.addButton.setOnClickListener {
            val name = binding.nameEdit.text.toString()
            val email = binding.emailEdit.text.toString()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val dateFrom = LocalDate.parse(fromAdd, formatter)
            val dateTo = LocalDate.parse(toAdd, formatter)
            viewLifecycleOwner.lifecycleScope.launch {
                val awd = launch(Dispatchers.IO) {
                    DbWorker.newsDao.insertDataFromGuest(Guest(null, name, email, dateFrom, dateTo))
                }
                awd.join()
                viewModel.setCallbackValue(true)
                parentFragmentManager.beginTransaction().remove(this@AddGuestFragment).commit()
            }



        }
    }

    companion object {
        const val FRAGMENT_KEY_2="fragkey2"
        const val BUNDLE_KEY_2="bundlekey2"
        @JvmStatic
        fun newInstance(check:Boolean) = AddGuestFragment().apply {
            arguments= bundleOf(BUNDLE_KEY_2 to check)
        }
    }
}