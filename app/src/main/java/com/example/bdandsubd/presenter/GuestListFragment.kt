package com.example.bdandsubd.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bdandsubd.DbWorker
import com.example.bdandsubd.MyClickListener
import com.example.bdandsubd.databinding.FragmentItemsListBinding
import com.example.bdandsubd.recycle.RecycleAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GuestListFragment : Fragment() {
    lateinit var binding: FragmentItemsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsListBinding.inflate(inflater)
        viewLifecycleOwner.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val date = DbWorker.newsDao.getDataFromGuest()
                val adapter = RecycleAdapter(date, 3)
                requireActivity().runOnUiThread {
                    binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
                    binding.recycleView.adapter = adapter
                }
            }


        }


        return binding.root
    }


    companion object {

        @JvmStatic
        fun newInstance() = GuestListFragment()
    }
}