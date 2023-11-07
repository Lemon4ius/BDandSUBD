package com.example.bdandsubd.presenter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bdandsubd.MainActivity
import com.example.bdandsubd.recycle.listeners.GuestListener
import com.example.bdandsubd.dataBase.DbWorker
import com.example.bdandsubd.R
import com.example.bdandsubd.presenter.viewmodels.SharedViewModel
import com.example.bdandsubd.databinding.FragmentItemsListBinding
import com.example.bdandsubd.entities.getter.GuestGet
import com.example.bdandsubd.navigation.GuestlNavigation
import com.example.bdandsubd.presenter.addPresentor.AddGuestFragment
import com.example.bdandsubd.recycle.RecycleAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GuestListFragment : Fragment() {
    lateinit var binding: FragmentItemsListBinding
    lateinit var adapter: RecycleAdapter
    lateinit var guestlNavigation: GuestlNavigation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        guestlNavigation=context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsListBinding.inflate(inflater)
        adapterinit()
        nagigatioTopAppBar()
        nabigationBottomAppBar()

        val myviewmodel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        myviewmodel.getCallbackLiveData().observe(viewLifecycleOwner) { values ->

            viewLifecycleOwner.lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val data = DbWorker.newsDao.getDataFromGuest()
                    withContext(Dispatchers.Main) {
                        adapter.guestList = data
                    }
                }
            }
        }

        binding.addingButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(binding.frameLayoutDialog.id, AddGuestFragment.newInstance(false))
                .commit()
        }
        return binding.root
    }

    private fun adapterinit() {
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val date = DbWorker.newsDao.getDataFromGuest()
                adapter = RecycleAdapter(date, 3, object : GuestListener {
                    override fun editGuest(guest: GuestGet) {
                        setFragmentResult(
                            AddGuestFragment.FRAGMENT_KEY_2, bundleOf(
                                BUNDLE_RESULT_KEY to guest
                            )
                        )
                        parentFragmentManager
                            .beginTransaction()
                            .replace(
                                binding.frameLayoutDialog.id,
                                AddGuestFragment.newInstance(true)
                            )
                            .commit()
                    }

                    override suspend fun deleteGuest(guest: GuestGet) {
                        withContext(Dispatchers.IO) {
                            DbWorker.newsDao.deleteGuest(guest.id)
                            val updateGuest = DbWorker.newsDao.getDataFromGuest()
                            withContext(Dispatchers.Main) {
                                adapter.guestList = updateGuest
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

    private fun nabigationBottomAppBar() {
        binding.cancelButton.setOnClickListener {
            binding.toolbar.visibility = View.GONE
            adapter.dissableCheckBox()
        }
        binding.deleteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                adapter.deleteGuest()
            }
        }
    }

    private fun nagigatioTopAppBar() {

        binding.topAppBar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.trash -> {
                    binding.toolbar.visibility = View.VISIBLE
                    adapter.checkBoxActive()
                    true
                }
                R.id.search_bar->{
                    guestlNavigation.openGuestSearch()
                    true
                }
                R.id.report->{
                    guestlNavigation.reportGuest("Guest")
                    true
                }

                else -> false
            }
        }
    }


    companion object {
        const val BUNDLE_RESULT_KEY = "bundlekey"

        @JvmStatic
        fun newInstance() = GuestListFragment()
    }
}