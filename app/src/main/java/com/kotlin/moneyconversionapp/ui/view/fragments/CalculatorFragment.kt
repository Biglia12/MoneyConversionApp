package com.kotlin.moneyconversionapp.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.databinding.FragmentCalculatorBinding
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
        dollarViewModel.casaResponse.observe(viewLifecycleOwner, Observer {
            val arraNames = arrayListOf<String>()
           it.forEach {
              arraNames.add(it.dollarCasa.nombre.toString())
           }
            val adapterSpinner = activity?.let { it1 -> ArrayAdapter(it1, android.R.layout.simple_spinner_item, arraNames) }
            binding.spinnerChoose.adapter = adapterSpinner
            val spinnerSelected = binding.spinnerChoose.selectedItem.toString()

            binding.spinnerChoose.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                   // Toast.makeText(requireActivity(), arrayNames[position], Toast.LENGTH_SHORT).show()
                    val names = arraNames[position]
                    when (names){
                        "Dolar Oficial" -> setPrices(it[position])
                        "Dolar Blue" -> setPrices(it[position])
                        "Dolar Soja" -> setPrices(it[position])
                        "Dolar Contado con Liqui" -> setPrices(it[position])
                        "Dolar Bolsa" -> setPrices(it[position])
                        "Bitcoin" -> setPrices(it[position])
                        "Dolar turista" -> setPrices(it[position])
                        "Dolar" -> setPrices(it[position])
                        else -> setWithoutPrices("$0")
                    }
                   /* if (arraNames[position] == "Dolar Oficial" || ){
                        binding.textSellPrice.text = it[position].dollarCasa.venta
                        binding.textBuyPrice.text = it[position].dollarCasa.venta
                    }else if (arraNames[position] == "Dolar Oficial"){
                        binding.textSellPrice.text = it[position].dollarCasa.venta
                        binding.textBuyPrice.text = it[position].dollarCasa.venta
                    }else if (arraNames[position] == "Dolar Blue"){
                        binding.textSellPrice.text = it[position].dollarCasa.venta
                        binding.textBuyPrice.text = it[position].dollarCasa.venta
                    }else if (arraNames[position] == "Dolar Soja"){
                        binding.textSellPrice.text = it[position].dollarCasa.venta
                        binding.textBuyPrice.text = it[position].dollarCasa.venta
                    }*/
                }

            }
        })
    }

    private fun setWithoutPrices(priceWithCero: String) {
        binding.textSellPrice.text = priceWithCero
        binding.textBuyPrice.text = priceWithCero

    }

    private fun setPrices(casaResponse: CasaResponse) {
        val priceWithDollarVenta = "$" + casaResponse.dollarCasa.venta
        val priceWithDollarCompra = "$" + casaResponse.dollarCasa.compra

        binding.textSellPrice.text = priceWithDollarVenta
        binding.textBuyPrice.text = priceWithDollarCompra
    }

}