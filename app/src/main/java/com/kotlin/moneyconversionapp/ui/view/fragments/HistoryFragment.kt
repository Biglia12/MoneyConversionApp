package com.kotlin.moneyconversionapp.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.FragmentDashBoardBinding
import com.kotlin.moneyconversionapp.databinding.FragmentHistoryBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModel
import com.kotlin.moneyconversionapp.ui.viewmodel.HistoricDollarViewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historicDollarViewModel: HistoricDollarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historicDollarViewModel.historicDollarResponse.observe(viewLifecycleOwner, Observer {
            //initRecyler(it)
        })

    }

}