package com.kotlin.moneyconversionapp.ui.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.FragmentCalculatorBinding
import com.kotlin.moneyconversionapp.databinding.FragmentDashBoardBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModel

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private val dollarViewModel: DollarViewModel by viewModels()
   // private val arrayNames = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculatorBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinner()
        valueEt()

        }

    private fun valueEt() {
        binding.btnCalculate.setOnClickListener {

            val valueEtString = binding.editTextCalculate.text
            val valueStringWithDollar = "$$valueEtString"

            if (valueEtString.isNotEmpty()) {
                binding.textSellPriceMount.text = valueStringWithDollar
                binding.textBuyPriceMount.text = valueStringWithDollar
            }else{
                binding.textSellPriceMount.text = "$0"
                binding.textBuyPriceMount.text = "$0"
            }
        }

    }

    private fun setSpinner() {
        dollarViewModel.array.observe(viewLifecycleOwner, Observer {
            val adapterSpinner = activity?.let { it1 -> ArrayAdapter(it1, android.R.layout.simple_spinner_item, it) }
            binding.spinnerChoose.adapter = adapterSpinner
            val spinnerSelected = binding.spinnerChoose.selectedItem.toString()

            binding.spinnerChoose.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                   // Toast.makeText(requireActivity(), arrayNames[position], Toast.LENGTH_SHORT).show()
                    if (it[position] == "Dolar"){
                        Toast.makeText(requireActivity(), "arrayNames[position]", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        })
    }

}