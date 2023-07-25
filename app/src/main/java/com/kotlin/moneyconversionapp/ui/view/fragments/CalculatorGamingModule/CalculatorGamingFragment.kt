package com.kotlin.moneyconversionapp.ui.view.fragments.CalculatorGamingModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.FragmentCalculatorBinding
import com.kotlin.moneyconversionapp.databinding.FragmentCalculatorGamingBinding
import javax.inject.Inject

class CalculatorGamingFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentCalculatorGamingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculatorGamingBinding.inflate(inflater)
        return binding.root
    }

}