package com.example.bdandsubd.presenter

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.databinding.FragmentDiagramsBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PieDiagramsFragment : Fragment() {
    lateinit var binding:FragmentDiagramsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentDiagramsBinding.inflate(inflater)
        charForHotel()
        charForHotelPrice()



        return binding.root
    }

    private fun charForHotelPrice() {
        val listEnt=ArrayList<PieEntry>()
        CoroutineScope(Dispatchers.IO).launch {
            val data=DbWorker.newsDao.getDateFromHotel()
            withContext(Dispatchers.IO){
                data.forEach{
                    listEnt.add(PieEntry(it.starRating.toFloat(), it.name))
                }
                val dataSet = PieDataSet(listEnt, "Сравнение звезд отелей")
                dataSet.colors = listOf(Color.RED, Color.GREEN, Color.BLUE) // Цвета секторов

                val pieData = PieData(dataSet)

                // Настройка внешнего вида круговой диаграммы

                binding.secondBarChart.data = pieData
                binding.secondBarChart.description.isEnabled = true
                binding.secondBarChart.isDrawHoleEnabled = true



                // Обновление диаграммы
                binding.secondBarChart.invalidate()
            }
        }
    }


    private fun charForHotel() {
        val listEnt=ArrayList<PieEntry>()
        CoroutineScope(Dispatchers.IO).launch {
            val data=DbWorker.newsDao.getDateFromHotel()
            withContext(Dispatchers.IO){
                data.forEach{
                    listEnt.add(PieEntry(it.roomCount+1f, it.name))
                }
                val dataSet = PieDataSet(listEnt, "Количество комнат")
                dataSet.colors = listOf(Color.RED, Color.GREEN, Color.BLUE) // Цвета секторов

                val pieData = PieData(dataSet)

                // Настройка внешнего вида круговой диаграммы

                binding.firstPieChart.data = pieData
                binding.firstPieChart.description.isEnabled = true
                binding.firstPieChart.isDrawHoleEnabled = true


                // Обновление диаграммы
                binding.firstPieChart.invalidate()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PieDiagramsFragment()
    }
}