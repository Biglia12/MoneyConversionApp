package com.kotlin.moneyconversionapp.ui.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.databinding.FragmentCalculatorBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModel

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!
    private val dollarViewModel: DollarViewModel by viewModels()
    private  var priceWithDollarVenta : String = ""
    private  var priceWithDollarCompra : String = ""
    private lateinit var valueEtString : String

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
        sharePrice()

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

    private fun btnCalculateListener() {

        binding.btnCalculate.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                valueEtString = binding.editTextCalculate.text.toString()
                dollarViewModel.setCalculate(valueEtString, priceWithDollarCompra, priceWithDollarVenta) // se pasan los parametro para poder hacer la cuenta
            }
        })
        //callViewModelCalculate()//el viewModel nos pasara el calculo de la cuenta(lo hicmos en una funcion por q lo llamaremos  cuando selecionamos el spinner y cuando tocamos el boton calcular)
    }

    private fun sharePrice() {
        binding.imgShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Hey Check out this Great app:")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }

    private fun setWithoutPrices(priceWithCero: String) {
        binding.textSellPrice.text = priceWithCero
        binding.textBuyPrice.text = priceWithCero

    }

    private fun setPrices(casaResponse: CasaResponse) {
         priceWithDollarVenta = casaResponse.dollarCasa.venta.toString()
         priceWithDollarCompra = casaResponse.dollarCasa.compra.toString()

        binding.textSellPrice.text = stringWithDollarSign(priceWithDollarVenta)
        binding.textBuyPrice.text = stringWithDollarSign(priceWithDollarCompra)

        dollarViewModel.setCalculate(binding.editTextCalculate.text.toString(), priceWithDollarCompra, priceWithDollarVenta)

        callViewModelCalculate() //el viewModel nos pasara el calculo de la cuenta(lo hicmos en una funcion por q lo llamaremos  cuando selecionamos el spinner y cuando tocamos el boton calcular)

    }


    private fun callViewModelCalculate() {

        dollarViewModel.resultCalculateSell.observe(viewLifecycleOwner, Observer {
            binding.textSellPriceMount.text = stringWithDollarSign(it)
        })

        dollarViewModel.resultCalculateBuy.observe(viewLifecycleOwner, Observer {
            binding.textBuyPriceMount.text = stringWithDollarSign(it)
        })

    }

    private fun stringWithDollarSign(price: String?): String? {
        return if (price.equals("No Cotiza")){
            price
        }else {
            Constants.DOLLAR_SIGN + price
        }
    }


    @SuppressLint("RestrictedApi")
    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar!!.setShowHideAnimationEnabled(false) // esto no permitira cuando el action bar se oculte haga una animacion
        (activity as AppCompatActivity).supportActionBar!!.hide() // se oculta el action bar
    }

    @SuppressLint("RestrictedApi")
    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.setShowHideAnimationEnabled(false)
        (activity as AppCompatActivity).supportActionBar!!.show()
    }


    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.setShowHideAnimationEnabled(false)
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

}