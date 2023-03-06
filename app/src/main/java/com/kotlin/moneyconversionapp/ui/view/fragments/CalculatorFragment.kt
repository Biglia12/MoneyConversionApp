package com.kotlin.moneyconversionapp.ui.view.fragments

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.databinding.FragmentCalculatorBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModel
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModelFactory


class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private val dollarViewModel: DollarViewModel by viewModels(){
        DollarViewModelFactory(requireActivity().application)
    }

    private  var priceWithDollarVenta : String = ""
    private  var priceWithDollarCompra : String = ""
    private lateinit var valueEtString : String
    //rivate val moneyApplication: MoneyApplication = MoneyApplication(requireContext().applicationContext)

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
        if (dollarViewModel.moneyApplication.isConnected()) {
            spinner()
         /*   dollarViewModel.casaResponseCalculator.observe(viewLifecycleOwner, Observer {
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
            })*/
        }else {
           if (dollarViewModel.moneyApplication.getDollarValue(Constants.DOLLAR_VALUE) != null){
               //spinner(dollarViewModel.moneyApplication.getDollarValue())
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
                when (names) {
                    "Dolar Oficial" -> setPrices(casaResponses[position]) //Al ser un servicio de terceros puede haber problemas con esto, pero no hay otra manera por el momento
                    "Dolar Blue" -> setPrices(casaResponses[position])
                    "Dolar Soja" -> setPrices(casaResponses[position])
                    "Dolar Contado con Liqui" -> setPrices(casaResponses[position])
                    "Dolar Bolsa" -> setPrices(casaResponses[position])
                    "Bitcoin" -> setPrices(casaResponses[position])
                    "Dolar turista" -> setPrices(casaResponses[position])
                    "Dolar" -> setPrices(casaResponses[position])
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
        binding.imgShare.setOnClickListener {
            view?.let { it1 -> screenShot(it1)?.let { it1 -> share(it1) } }
        }
    }

    private fun screenShot(view: View): Bitmap? {

        val rootView: View = requireView().rootView
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        rootView.isDrawingCacheEnabled = true
        return rootView.drawingCache
    }

    private fun share(bitmap: Bitmap) {
        val pathofBmp = MediaStore.Images.Media.insertImage(
            requireActivity().contentResolver,
            bitmap, "title", null
        )
        val uri: Uri = Uri.parse(pathofBmp)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Star App")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Precio de compra:\$$priceWithDollarCompra\nPrecio de venta: \$$priceWithDollarVenta")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        requireActivity().startActivity(Intent.createChooser(shareIntent, "hello hello"))
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