package com.example.bdandsubd.presenter

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.bdandsubd.R
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentBarChartBinding
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BarChartFragment : Fragment() {
    lateinit var binding:FragmentBarChartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentBarChartBinding.inflate(inflater)
        barchartRoomPrice()
        barchartRoom()
        pieChartRoomPrice()
        return binding.root
    }

    private fun pieChartRoomPrice() {
        var dataList=ArrayList<PieEntry>()
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO){
                val date =DbWorker.newsDao.getDataFromRoom()
                withContext(Dispatchers.Main){
                    date.forEach {
                        dataList.add(PieEntry(it.price.toFloat(),it.roomNumber))
                    }
                    val dataSet = PieDataSet(dataList, "Длина имени гостя")
                    dataSet.colors = listOf(Color.RED, Color.GREEN, Color.BLUE) // Цвета секторов
                    val barData = PieData(dataSet)
                    // Настройка внешнего вида гистограммы

                    binding.paramPieChart.data = barData
                    binding.paramPieChart.description.isEnabled = false

                    binding.price.doOnTextChanged { text, start, before, count ->
                        Log.e("Text",text.toString())
                        date.forEach {
                            if (text!!.isNotEmpty()) {
                                if (it.price.toFloat() < text.toString().toFloat()) {

                                    var index = -1
                                    var i=0

                                    dataList.forEach { item ->
                                        if (item.value == it.price.toFloat()) {
                                            index=i
                                        }
                                        i++
                                    }
                                    binding.paramPieChart.highlightValue(
                                        index.toFloat(),
                                        0,
                                        false
                                    )
                                    binding.paramPieChart.invalidate()
//                                else{
//                                    binding.paramPieChart.highlightValue(1f, 0, false)
//                                    binding.paramPieChart.invalidate()
//                                }
                                }
                            }
                        }
                    }

                    binding.paramPieChart.highlightValue(1f, -2, false)
                    binding.paramPieChart.invalidate()

                }
            }

        }
    }

    private fun barchartRoom() {
        var dataList=ArrayList<BarEntry>()
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO){
                val date =DbWorker.newsDao.getDataFromGuest()
                withContext(Dispatchers.Main){
                    date.forEach {
                        dataList.add(BarEntry(it.id.toFloat(),it.name.length.toFloat()))
                    }
                    val dataSet = BarDataSet(dataList, "Длина имени гостя")
                    dataSet.color = Color.RED
                    val barData = BarData(dataSet)
                    // Настройка внешнего вида гистограммы
                    binding.seconddig.data = barData
                    binding.seconddig.description.isEnabled = false
                    binding.seconddig.setFitBars(true)
                    // Обновление гистограммы
                    binding.seconddig.invalidate()
                }
            }

        }
    }

    private fun barchartRoomPrice() {
        var dataList=ArrayList<BarEntry>()

        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO){
                val date =DbWorker.newsDao.getDataFromRoom()
                withContext(Dispatchers.Main){
                    date.forEach {
                        dataList.add(BarEntry(it.id!!.toFloat(),it.price.toFloat()))
                    }
                    val dataSet = BarDataSet(dataList, "Цена комнаты")
                    dataSet.color = Color.BLUE // Цвет столбцов

                    val barData = BarData(dataSet)

                    // Настройка внешнего вида гистограммы
                    binding.firstdig.data = barData
                    binding.firstdig.description.isEnabled = false
                    binding.firstdig.setFitBars(true)

                    // Обновление гистограммы
                    binding.firstdig.invalidate()
                }
            }

        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =BarChartFragment()
    }
}