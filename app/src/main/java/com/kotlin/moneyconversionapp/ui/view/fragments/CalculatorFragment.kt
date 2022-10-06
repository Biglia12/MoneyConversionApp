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
    private lateinit var priceWithDollarVenta : String
    private lateinit var priceWithDollarCompra : String

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
        btnCalculateListener()

        }

    private fun btnCalculateListener() {

        binding.btnCalculate.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {

                val valueEtString = binding.editTextCalculate.text.toString()
                dollarViewModel.setCalculate(valueEtString, priceWithDollarCompra, priceWithDollarVenta) // se pasan los parametro para poder hacer la cuenta
            }
        })

        dollarViewModel.getCalculateSell().observe(requireActivity(), object :Observer<String> { // se llama para obtenrer el resultado
            override fun onChanged(resultSellAccount: String?) {
                binding.textSellPriceMount.text = resultSellAccount
            }

        })
        dollarViewModel.getCalculateBuy().observe(requireActivity(), object :Observer<String> {
            override fun onChanged(resultBuyAccount: String?) {
                binding.textBuyPriceMount.text = resultBuyAccount
            }

        })

    }

    private fun setSpinner() {
        dollarViewModel.casaResponseCalculator.observe(viewLifecycleOwner, Observer {
            val arrayNames = arrayListOf<String>()
           it.forEach {
              arrayNames.add(it.dollarCasa.nombre.toString())
           }
            val adapterSpinner = activity?.let { it1 -> ArrayAdapter(it1, android.R.layout.simple_spinner_item, arrayNames) }
            binding.spinnerChoose.adapter = adapterSpinner

            binding.spinnerChoose.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val names = arrayNames[position]
                    when (names) {
                        "Dolar Oficial" -> setPrices(it[position]) //Al ser un servicio de terceros puede haber problemas con esto, pero no hay otra manera por el momento
                        "Dolar Blue" -> setPrices(it[position])
                        "Dolar Soja" -> setPrices(it[position])
                        "Dolar Contado con Liqui" -> setPrices(it[position])
                        "Dolar Bolsa" -> setPrices(it[position])
                        "Bitcoin" -> setPrices(it[position])
                        "Dolar turista" -> setPrices(it[position])
                        "Dolar" -> setPrices(it[position])
                        else -> setWithoutPrices("$0")
                    }
                }

            }
        })
    }

    private fun setWithoutPrices(priceWithCero: String) {
        binding.textSellPrice.text = priceWithCero
        binding.textBuyPrice.text = priceWithCero

    }

    private fun setPrices(casaResponse: CasaResponse) {
         priceWithDollarVenta = casaResponse.dollarCasa.venta.toString()
         priceWithDollarCompra = casaResponse.dollarCasa.compra.toString()

        binding.textSellPrice.text = priceWithDollarVenta
        binding.textBuyPrice.text = priceWithDollarCompra
        binding.textSellPriceMount.text = priceWithDollarVenta
        binding.textBuyPriceMount.text = priceWithDollarCompra
    }

}