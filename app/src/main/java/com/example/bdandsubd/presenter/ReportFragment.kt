package com.example.bdandsubd.presenter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.os.bundleOf
import com.example.bdandsubd.R
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentReportBinding
import com.example.bdandsubd.entities.getter.GuestGet
import com.example.bdandsubd.entities.getter.HotelGet
import com.example.bdandsubd.entities.getter.RoomGet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import java.util.Objects

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReportFragment : Fragment() {
    lateinit var binding: FragmentReportBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater)

        if (arguments?.containsKey(BUNDLE_REPORT_HOTEL_KEY) == true) {
            binding.title.text="Отчет по отелям"
            tableHotelInit()
        } else if (arguments?.containsKey(BUNDLE_REPORT_ROOM_KEY) == true) {
            binding.title.text="Отчет по комнатам"
            tableRoomInit()
        } else {
            binding.title.text="Отчет по гостям"
            tableGuestInit()
        }

        return binding.root
    }

    private fun tableHotelInit() {
        CoroutineScope(Dispatchers.IO).launch {
            val date = DbWorker.newsDao.getDateFromHotel()
            withContext(Dispatchers.Main) {
                val tableLayout = binding.tableLayout
                val headerRow = TableRow(requireContext())

                val headerIdCell = TextView(requireContext())
                headerIdCell.text = "ID"
                headerIdCell.setBackgroundResource(R.drawable.cell_background)
                headerRow.addView(headerIdCell)

                val headerNameHotelCell = TextView(requireContext())
                headerNameHotelCell.text = "Название отеля"
                headerRow.addView(headerNameHotelCell)

                val headerNameGuestCell = TextView(requireContext())
                headerNameGuestCell.text = "Адрес"
                headerRow.addView(headerNameGuestCell)

                val headerRoomNumberCell = TextView(requireContext())
                headerRoomNumberCell.text = "Количество звезд"
                headerRow.addView(headerRoomNumberCell)

                val headerTypeCell = TextView(requireContext())
                headerTypeCell.text = "Количество комнат"
                headerRow.addView(headerTypeCell)


                // Добавьте заголовки в таблицу
                tableLayout.addView(headerRow)

                var resultAllPrice = 0

                for (hotel in date) {
                    val row = TableRow(requireContext())

                    val idCell = TextView(requireContext())
                    idCell.text = hotel.id?.toString() ?: ""
                    idCell.setBackgroundResource(R.drawable.cell_background)
                    row.addView(idCell)

                    val nameHotelCell = TextView(requireContext())
                    nameHotelCell.text = hotel.name
                    row.addView(nameHotelCell)

                    val nameGuestCell = TextView(requireContext())
                    nameGuestCell.text = hotel.address
                    row.addView(nameGuestCell)

                    val roomNumberCell = TextView(requireContext())
                    roomNumberCell.text = hotel.starRating.toString()
                    row.addView(roomNumberCell)

                    val typeCell = TextView(requireContext())
                    typeCell.text = hotel.roomCount.toString()
                    row.addView(typeCell)


                    // Добавьте строку в таблицу
                    tableLayout.addView(row)


                }
                date.forEach {
                    resultAllPrice += it.starRating
                }
                binding.result.text = "Общие количество звезд: " + resultAllPrice
            }
        }
    }

    private fun tableGuestInit() {
        CoroutineScope(Dispatchers.IO).launch {
            val date = DbWorker.newsDao.getDataFromGuest()
            withContext(Dispatchers.Main) {
                val tableLayout = binding.tableLayout
                val headerRow = TableRow(requireContext())

                val headerIdCell = TextView(requireContext())
                headerIdCell.text = "ID"
                headerIdCell.setBackgroundResource(R.drawable.cell_background)
                headerRow.addView(headerIdCell)

                val headerNameHotelCell = TextView(requireContext())
                headerNameHotelCell.text = "ФИО"
                headerRow.addView(headerNameHotelCell)

                val headerNameGuestCell = TextView(requireContext())
                headerNameGuestCell.text = "Почта"
                headerRow.addView(headerNameGuestCell)

                val headerRoomNumberCell = TextView(requireContext())
                headerRoomNumberCell.text = "Дата заселения"
                headerRow.addView(headerRoomNumberCell)

                val headerTypeCell = TextView(requireContext())
                headerTypeCell.text = "Дата выселения"
                headerRow.addView(headerTypeCell)

                // Добавьте заголовки в таблицу
                tableLayout.addView(headerRow)

                var resultAllPrice = 0

                for (guest in date) {
                    val row = TableRow(requireContext())

                    val idCell = TextView(requireContext())
                    idCell.text = guest.id.toString() ?: ""
                    idCell.setBackgroundResource(R.drawable.cell_background)
                    row.addView(idCell)

                    val nameHotelCell = TextView(requireContext())
                    nameHotelCell.text = guest.name
                    row.addView(nameHotelCell)

                    val nameGuestCell = TextView(requireContext())
                    nameGuestCell.text = guest.email
                    row.addView(nameGuestCell)

                    val roomNumberCell = TextView(requireContext())
                    roomNumberCell.text = guest.checkInDate.toString()
                    row.addView(roomNumberCell)

                    val typeCell = TextView(requireContext())
                    typeCell.text = guest.checkOutDate.toString()
                    row.addView(typeCell)


                    // Добавьте строку в таблицу
                    tableLayout.addView(row)


                }

                binding.result.text = "Количество гостей: " + date.size
            }
        }
    }

    private fun tableRoomInit() {
        CoroutineScope(Dispatchers.IO).launch {
            val date = DbWorker.newsDao.getDataFromRoom()
            withContext(Dispatchers.Main) {
                val tableLayout = binding.tableLayout
                val headerRow = TableRow(requireContext())

                val headerIdCell = TextView(requireContext())
                headerIdCell.text = "ID"
                headerIdCell.setBackgroundResource(R.drawable.cell_background)
                headerRow.addView(headerIdCell)

                val headerNameHotelCell = TextView(requireContext())
                headerNameHotelCell.text = "Название отеля"
                headerRow.addView(headerNameHotelCell)

                val headerNameGuestCell = TextView(requireContext())
                headerNameGuestCell.text = "Имя гостя"
                headerRow.addView(headerNameGuestCell)

                val headerRoomNumberCell = TextView(requireContext())
                headerRoomNumberCell.text = "Номер комнаты"
                headerRow.addView(headerRoomNumberCell)

                val headerTypeCell = TextView(requireContext())
                headerTypeCell.text = "Тип комнаты"
                headerRow.addView(headerTypeCell)

                val headerPriceCell = TextView(requireContext())
                headerPriceCell.text = "Цена"
                headerRow.addView(headerPriceCell)

                // Добавьте заголовки в таблицу
                tableLayout.addView(headerRow)

                var resultAllPrice = 0.0

                for (room in date) {
                    val row = TableRow(requireContext())

                    val idCell = TextView(requireContext())
                    idCell.text = room.id?.toString() ?: ""
                    idCell.setBackgroundResource(R.drawable.cell_background)
                    row.addView(idCell)

                    val nameHotelCell = TextView(requireContext())
                    nameHotelCell.text = room.nameHotel
                    row.addView(nameHotelCell)

                    val nameGuestCell = TextView(requireContext())
                    nameGuestCell.text = room.nameGuest
                    row.addView(nameGuestCell)

                    val roomNumberCell = TextView(requireContext())
                    roomNumberCell.text = "№: " + room.roomNumber.toString()
                    row.addView(roomNumberCell)

                    val typeCell = TextView(requireContext())
                    typeCell.text = room.type
                    row.addView(typeCell)

                    val priceCell = TextView(requireContext())
                    priceCell.text = "${room.price} Byn"
                    row.addView(priceCell)

                    // Добавьте строку в таблицу
                    tableLayout.addView(row)

                    resultAllPrice += room.price
                }

                binding.result.text = "Общая стоимость комнат: " + resultAllPrice
            }
        }
    }


    companion object {
        const val BUNDLE_REPORT_HOTEL_KEY = "reportHotelKey"
        const val BUNDLE_REPORT_ROOM_KEY = "reportRoomKey"
        const val BUNDLE_REPORT_GUEST_KEY = "reportGuestKey"

        @JvmStatic
        fun newInstance(type: String) =
            ReportFragment().apply {
                when (type) {
                    "Hotel" -> {
                        arguments = bundleOf(BUNDLE_REPORT_HOTEL_KEY to type)
                    }

                    "Room" -> {
                        arguments = bundleOf(BUNDLE_REPORT_ROOM_KEY to type)
                    }

                    "Guest" -> {
                        arguments = bundleOf(BUNDLE_REPORT_GUEST_KEY to type)
                    }
                }

            }

    }
}