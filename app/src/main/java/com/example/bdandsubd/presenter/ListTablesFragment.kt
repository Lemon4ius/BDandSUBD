package com.example.bdandsubd.presenter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bdandsubd.MainActivity
import com.example.bdandsubd.MainNavigation
import com.example.bdandsubd.MyClickListener
import com.example.bdandsubd.databinding.FragmentListTablesBinding
import com.example.bdandsubd.recycle.RecycleAdapter


class ListTablesFragment : Fragment() {

    lateinit var binding:FragmentListTablesBinding
    lateinit var mainNavigation: MainNavigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainNavigation=context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentListTablesBinding.inflate(inflater)
        var tables = listOf("Отели", "Комнаты", "Гости")
        val layoutMager=LinearLayoutManager(requireContext())
        val adapter= RecycleAdapter(tables,object: MyClickListener {
            override fun getGuestClick() {
                mainNavigation.goToGuest()
            }

            override fun getRoomClick() {
                mainNavigation.goToRoom()
            }

            override fun getHotelClick() {
                mainNavigation.goToHotel()
            }
        },1)
        binding.recycleView.adapter=adapter
        binding.recycleView.layoutManager=layoutMager
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListTablesFragment()
    }
}