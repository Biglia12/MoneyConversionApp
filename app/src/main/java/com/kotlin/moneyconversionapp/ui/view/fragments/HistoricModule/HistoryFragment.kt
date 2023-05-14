package com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.kotlin.moneyconversionapp.BuildConfig
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import com.kotlin.moneyconversionapp.databinding.FragmentHistoryBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.Historic.HistoricDollarViewModel
import kotlinx.android.synthetic.main.fragment_dash_board.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historicDollarViewModel: HistoricDollarViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = "Grafico Dólar"
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))

        historicDollarViewModel.loadData()

        funAdView() //funcion para publicidad

        observeLiveData()


    }

    private fun funAdView() {

        val adRequest = AdRequest.Builder().build()
        binding.adViewHistoric.loadAd(adRequest)


        binding.adViewHistoric.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                Log.e("onAdFailedToLoad",adError.toString())
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                binding.adViewHistoric.visibility = View.VISIBLE
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeLiveData() {

        historicDollarViewModel.loading.observe(viewLifecycleOwner, Observer {
            binding.progressHistory.isVisible = it
        })

        historicDollarViewModel.graphicVisible.observe(viewLifecycleOwner, Observer {
            binding.lineChartBlue.isVisible = it
            binding.lineChartOficial.isVisible = it
        })

        binding.imgBtnRefresh.setOnClickListener {
            historicDollarViewModel.reloadService()
        }

        historicDollarViewModel.historicDollarBlueLiveData.observe(viewLifecycleOwner, Observer {
            graphic(it)
        })

        historicDollarViewModel.historicDollarOficialLiveData.observe(viewLifecycleOwner, Observer {
            graphic(it)
        })
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun graphic(historicDollarModels: ArrayList<HistoricDollarModel>) {

        if (historicDollarModels[0].source == "Blue") {
            datesBlueOrOfical(historicDollarModels,binding.lineChartBlue)
        }else{
            datesBlueOrOfical(historicDollarModels,binding.lineChartOficial)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun datesBlueOrOfical(historicDollarModels: ArrayList<HistoricDollarModel>, lineChart: LineChart) {
        val lineDataSet: LineDataSet =
            LineDataSet(datavlues(historicDollarModels), historicDollarModels[0].source)

        val dataSet: ArrayList<ILineDataSet> = ArrayList()
        dataSet.add(lineDataSet)

        val data = LineData(dataSet)

        val xAxis =lineChart.xAxis
        xAxis.valueFormatter = datesForm(historicDollarModels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = 45f


        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.isDragDecelerationEnabled = true

        lineChart.data = data

        // Obtener la posición de la última entrada en el conjunto de datos del gráfico
        val lastPosition = lineChart.data.entryCount - 1

        // Establecer el rango máximo visible en el eje X
        lineChart.setVisibleXRangeMaximum(5f)

        // Mover la vista del gráfico a la última entrada
        lineChart.moveViewToX(lastPosition.toFloat())
        //lineChart.zoom(50f,50f,50f,50f)

        lineChart.invalidate()

    }



    private fun datesForm(historicDollarModels: ArrayList<HistoricDollarModel>): ValueFormatter? {
        return object : ValueFormatter() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun getFormattedValue(value: Float): String {
                val date = historicDollarModels[value.toInt()].date
                return LocalDate.parse(date).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun datavlues(historicDollarModels: ArrayList<HistoricDollarModel>): MutableList<Entry> {

        val dataVals = mutableListOf<Entry>()
        for (i in 0 until historicDollarModels.size) {
            val date = historicDollarModels[i].date
            val valueBuy = historicDollarModels[i].valueSell!!.toFloat()
            dataVals.add(Entry(i.toFloat(), valueBuy))
        }
        return dataVals


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