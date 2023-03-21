package com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import com.kotlin.moneyconversionapp.databinding.FragmentHistoryBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.Historic.HistoricDollarViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historicDollarViewModel: HistoricDollarViewModel by viewModels()

    private  var  dataVals: ArrayList<Entry> = ArrayList()

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

        historicDollarViewModel.loadData()

        binding.imgBtnRefresh.setOnClickListener {
            historicDollarViewModel.resetLoading()
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
        lineChart.isDragDecelerationEnabled = true;


        lineChart.data = data
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
            val valueBuy = historicDollarModels[i].valueBuy!!.toFloat()
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