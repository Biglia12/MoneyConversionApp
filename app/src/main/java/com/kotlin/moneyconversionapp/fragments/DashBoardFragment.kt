package com.kotlin.moneyconversionapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.adapters.DashBoardAdapter
import com.kotlin.moneyconversionapp.databinding.FragmentDashBoardBinding
import com.kotlin.moneyconversionapp.model.MoneyResponse

class DashBoardFragment : Fragment() {

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       initRecyler()
    }

    private fun initRecyler() {

        val moneyResponse: List<MoneyResponse> = listOf(
            MoneyResponse("pikachu", "truen"),
            MoneyResponse("bulbazoor", "agua")
        )
        binding.recyclerResumeFragment.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = DashBoardAdapter(moneyResponse)

        }

    }



}