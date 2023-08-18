package com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.kotlin.moneyconversionapp.MoneyApplication
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import com.kotlin.moneyconversionapp.databinding.FragmentHistoryBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.Historic.HistoricDollarViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dash_board.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment @Inject constructor() : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historicDollarViewModel: HistoricDollarViewModel by activityViewModels()
    private val moneyApplication: MoneyApplication = MoneyApplication()
    private var interstitial: InterstitialAd? = null

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


        funAdView() //funcion para publicidad
        funAdViewInter() //funcion para publicidad de Interstitial
        showAdsInterstitial()

        if (moneyApplication.isConnected(requireContext())) {
            observeLiveData()
        } else {
            binding.constraintErrorServiceHistoric.visibility = View.VISIBLE
        }

        btnReloadService()

    }


    private fun funAdViewInter() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(), requireActivity().resources.getString(R.string.ad_unit_id_interstitial), adRequest, object : InterstitialAdLoadCallback(){
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                interstitial = interstitialAd
            }
            override fun onAdFailedToLoad(p0: LoadAdError) {
                interstitial = null
            }
        })
    }

    private fun showAdsInterstitial() {
        interstitial?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }
            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            }
            override fun onAdShowedFullScreenContent() {
                interstitial = null
            }
        }
    }
    private fun funAdView() {

        val adRequest = AdRequest.Builder().build()
        binding.adViewHistoric.loadAd(adRequest)


        binding.adViewHistoric.adListener = object : AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
                Log.e("onAdFailedToLoad", adError.toString())
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

        historicDollarViewModel.error.observe(viewLifecycleOwner, Observer {
            binding.constraintErrorServiceHistoric.isVisible = it
        })

        historicDollarViewModel.graphicVisible.observe(viewLifecycleOwner, Observer {
            binding.lineChartBlue.isVisible = it
            binding.lineChartOficial.isVisible = it
        })

        historicDollarViewModel.historicDollarLiveData.observe(viewLifecycleOwner, Observer {
            graphic(it)
        })

    }

    private fun btnReloadService() {
        var count = 0
        binding.imgBtnRefresh.setOnClickListener {
            count++
            if (count == 2) { // Mostrar el anuncio cada dos taps
                showAds()
                count = 0
                funAdViewInter()
            }
            historicDollarViewModel.reloadService()
        }
    }

    fun showAds() {
        interstitial?.show(requireActivity())
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun graphic(historicDollarModels: ArrayList<HistoricDollarModel>) {

        val blueDollarModels = ArrayList(historicDollarModels.filter { it.source == "Blue" })
        datesBlueOrOfical(blueDollarModels, binding.lineChartBlue)

        val oficialDollarModels = ArrayList(historicDollarModels.filter { it.source == "Oficial" })
        datesBlueOrOfical(oficialDollarModels, binding.lineChartOficial)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun datesBlueOrOfical(
        historicDollarModels: ArrayList<HistoricDollarModel>,
        lineChart: LineChart
    ) {
        val lineDataSet: LineDataSet =
            LineDataSet(datavlues(historicDollarModels), historicDollarModels[0].source)

        val dataSet: ArrayList<ILineDataSet> = ArrayList()
        dataSet.add(lineDataSet)

        val data = LineData(dataSet)

        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = datesForm(historicDollarModels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = 45f


        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.isDragDecelerationEnabled = true
        lineChart.description.isEnabled = false

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

}