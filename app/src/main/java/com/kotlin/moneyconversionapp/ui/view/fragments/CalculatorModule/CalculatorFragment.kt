package com.kotlin.moneyconversionapp.ui.view.fragments.CalculatorModule

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotlin.moneyconversionapp.BuildConfig
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.MoneyApplication
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.databinding.FragmentCalculatorBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.Calculator.CalculatorViewModel
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calculator.constraint_calculator
import javax.inject.Inject

@AndroidEntryPoint
class CalculatorFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private val dollarViewModel: DollarViewModel by viewModels()
   // private val calculatorViewModel: CalculatorViewModel by activityViewModels()

    private  var priceWithDollarVenta : String = ""
    private  var priceWithDollarCompra : String = ""
    private lateinit var valueEtString : String

    private val moneyApplication: MoneyApplication = MoneyApplication()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculatorBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = "Calculadora"
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        binding.versionApp.text = BuildConfig.VERSION_NAME

        setSpinner()
        btnCalculateListener()
        sharePrice()

        }


    private fun setSpinner() {
        if (moneyApplication.isConnected(requireContext())) {
            spinner()
        }else {
           if (moneyApplication.getDollarValue(requireContext(),Constants.DOLLAR_VALUE) != null){
               dollarViewModel.setSpinner(moneyApplication.getDollarValue(requireContext(),Constants.DOLLAR_VALUE)!!)//le pasamos lo que tenemos almacenado al view model para poder seguir con la calculadora con los ultimos datos guardados
               spinner()
           }
            Toast.makeText(activity,"No hay conexion",Toast.LENGTH_SHORT).show()
        }
    }


    fun spinner() {
        dollarViewModel.casaResponseCalculator.observe(viewLifecycleOwner, Observer {
            val arrayNames = arrayListOf<String>()
            it.forEach {
                    arrayNames.add(it.dollarCasa.nombre.toString())
                }

            val adapterSpinner = activity?.let { it1 ->
                ArrayAdapter(
                    it1,
                    android.R.layout.simple_spinner_item,
                    arrayNames
                )
            }
            binding.spinnerChoose.adapter = adapterSpinner

            setSpinnerSelection(arrayNames, it)
        })
    }

   private fun setSpinnerSelection(
        arrayNames: ArrayList<String>,
        casaResponses: ArrayList<CasaResponse>
    ) {
        binding.spinnerChoose.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val names = arrayNames[position]
                val selectedCasaResponse = casaResponses[position]
                when (names) {
                    Constants.DOLLAR_OFICIAL,
                    Constants.DOLLAR_BLUE,
                    Constants.DOLLAR_SOJA,
                    Constants.DOLLAR_CONTADO_LIQUI,
                    Constants.DOLLAR_BOLSA,
                    Constants.BITCOIN,
                    Constants.DOLLAR_TRUISTA,
                    Constants.DOLLAR -> setPrices(selectedCasaResponse)
                    else -> setWithoutPrices("$0")
                }
            }

        }

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

    private fun sharePrice() {
        val priceBuy = resources.getString(R.string.price_buy)
        val priceSell = resources.getString(R.string.price_sell)
        binding.imgShare.setOnClickListener {
            generateShareIntent(priceBuy, priceSell)
        }
    }

    private fun generateShareIntent(priceBuy: String, priceSell: String) {
        val bitmap = screenShot()

        val timestamp = System.currentTimeMillis()
        val fileName = "capture_$timestamp.jpg"

        val pathOfBmp = MediaStore.Images.Media.insertImage(
            requireContext().contentResolver,
            bitmap, fileName, null
        )

        val uri: Uri = Uri.parse(pathOfBmp)

        val appPackageName = requireActivity().packageName
        val playStoreUrl = "https://play.google.com/store/apps/details?id=$appPackageName"

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "DólarArg")

        val message ="$priceBuy\$$priceWithDollarCompra\n$priceSell \$$priceWithDollarVenta\n\nDescarga nuestra app: $playStoreUrl"

        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(shareIntent, "hello hello"))
    }

    private fun screenShot(): Bitmap {
        // Invalidar la vista para forzar la actualización del sistema de caché de Android
        binding.constraintCalculator.invalidate()

        // Tomar la captura de pantalla actualizada
        //val rootView: View = view.rootView
        val bitmap = Bitmap.createBitmap(binding.constraintCalculator.width, binding.constraintCalculator.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        binding.constraintCalculator.draw(canvas)
        binding.constraintCalculator.isDrawingCacheEnabled = true
        return binding.constraintCalculator.drawingCache
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