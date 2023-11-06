package com.example.bdandsubd.presenter

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
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
import com.example.bdandsubd.databinding.FragmentHotelListBinding
import com.example.bdandsubd.entities.getter.HotelGet
import com.example.bdandsubd.navigation.HotelNavigation
import com.example.bdandsubd.presenter.addPresentor.AddHotelFragment
import com.example.bdandsubd.presenter.viewmodels.SharedHotelViewModel
import com.example.bdandsubd.recycle.RecycleAdapter
import com.example.bdandsubd.recycle.listeners.HotelListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HotelListFragment : Fragment() {
    lateinit var binding: FragmentHotelListBinding
    lateinit var adapter: RecycleAdapter
    lateinit var hotelNavigation: HotelNavigation
    override fun onAttach(context: Context) {
        super.onAttach(context)
        hotelNavigation=context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelListBinding.inflate(inflater)
        appbarNav()
        deleteData()
        binding.addingButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(binding.frameLayoutDialog.id, AddHotelFragment.newInstance(false))
                .commit()
        }
        updateAdapter()
        initAdapter()
        requireActivity()

        return binding.root
    }



    private fun initAdapter() {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val hotelList = DbWorker.newsDao.getDateFromHotel()
                adapter = RecycleAdapter(hotelList, 5, object : HotelListener {
                    override fun editHotel(hotel: HotelGet) {
                        setFragmentResult(FRAGMENT_KEY, bundleOf(BUNDLE_KEY to hotel))
                        parentFragmentManager.beginTransaction()
                            .replace(binding.frameLayoutDialog.id, AddHotelFragment.newInstance(true))
                            .commit()
                    }

                    override suspend fun deleteHotel(hotel: HotelGet) {
                        withContext(Dispatchers.IO) {
                            DbWorker.newsDao.deleteHotel(hotel.id!!)
                            val updateGuest = DbWorker.newsDao.getDateFromHotel()
                            withContext(Dispatchers.Main) {
                                adapter.hotelList = updateGuest
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

    private fun updateAdapter() {
        val myviewmodel = ViewModelProvider(requireActivity()).get(SharedHotelViewModel::class.java)

        myviewmodel.getCallbackLiveData().observe(viewLifecycleOwner) { value ->
            viewLifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val data = DbWorker.newsDao.getDateFromHotel()
                    withContext(Dispatchers.Main) {
                        adapter.hotelList = data
                    }
                }
            }
        }
    }

    private fun deleteData() {
        binding.deleteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                adapter.deleteHotel()
            }
        }
        binding.cancelButton.setOnClickListener {
            binding.toolbar.visibility = View.GONE
            adapter.dissableCheckBox()
        }
    }

    private fun appbarNav() {
        binding.topAppBar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.trash -> {
                        binding.toolbar.visibility = View.VISIBLE
                        adapter.checkBoxActive()

                    }
                    R.id.search_bar->{
                        hotelNavigation.openSearch()

                    }
                    else -> false
                }

                return true
            }
        })
    }



    private fun searchHotel() {

    }

    companion object {
        const val FRAGMENT_KEY="frag_key"
        const val BUNDLE_KEY="bundlekey"
        @JvmStatic
        fun newInstance() = HotelListFragment()
    }
}