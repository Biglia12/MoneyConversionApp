package com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        //graphic(it)

        binding.imgBtnRefresh.setOnClickListener {
            historicDollarViewModel.resetLoading()
        }

        historicDollarViewModel.historicDollarBlueLiveData.observe(viewLifecycleOwner, Observer {
              graphic(it)
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun graphic(historicDollarModels: ArrayList<HistoricDollarModel>) {

        val lineDataSet: LineDataSet = LineDataSet(datavlues(historicDollarModels), historicDollarModels[0].source)

        val dataSet: ArrayList<ILineDataSet> = ArrayList()
        dataSet.add(lineDataSet)

        val  data = LineData(dataSet)
        //binding.barChart.data = data

        val xAxis = binding.barChart.xAxis
        xAxis.valueFormatter = datesForm(historicDollarModels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        binding.barChart.setTouchEnabled(true)
        binding.barChart.isDragEnabled = true
        binding.barChart.setScaleEnabled(false)

        binding.barChart.data = data
        binding.barChart.invalidate()

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
    private fun datavlues(historicDollarModels: ArrayList<HistoricDollarModel>): MutableList<Entry>? {

       // datesForm(historicDollarModels)

        val dataVals = mutableListOf<Entry>()
        for (i in 0 until historicDollarModels.size) {
            val date = historicDollarModels[i].date
            val valueBuy = historicDollarModels[i].valueBuy!!.toFloat()
            dataVals.add(Entry(i.toFloat(), valueBuy))
        }
        return dataVals


     /*  dataVals.add(Entry(0f,20f))
       dataVals.add(Entry(1f,24f))
       dataVals.add(Entry(2f,28f))
       dataVals.add(Entry(3f,32f))
       dataVals.add(Entry(4f, 36F))

        return dataVals*/
    }

   /* @RequiresApi(Build.VERSION_CODES.O)
    private fun datesForm(historicDollarModels: ArrayList<HistoricDollarModel>): String? {
        var formattedDate = ""
        for (i in historicDollarModels){
            val dateString = i.date
            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(dateString, dateFormat)

            val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            formattedDate = dateFormatter.format(date)

            return formattedDate
        }
       return formattedDate

    }*/


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