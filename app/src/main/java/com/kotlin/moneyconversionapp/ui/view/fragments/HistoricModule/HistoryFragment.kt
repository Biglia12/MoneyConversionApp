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

        val xAxis = binding.barChart.xAxis
        xAxis.valueFormatter = datesForm(historicDollarModels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelRotationAngle = 45f


        binding.barChart.setTouchEnabled(true)
        binding.barChart.isDragEnabled = true
        binding.barChart.setScaleEnabled(true)
        binding.barChart.setPinchZoom(true)
        binding.barChart.isDragDecelerationEnabled = true;


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
    private fun datavlues(historicDollarModels: ArrayList<HistoricDollarModel>): MutableList<Entry> {
        val dataVals = mutableListOf<Entry>()
        for (i in 0 until historicDollarModels.size) {
            val date = historicDollarModels[i].date
            val valueBuy = historicDollarModels[i].valueBuy!!.toFloat()
            dataVals.add(Entry(i.toFloat(), valueBuy))
        }
        return dataVals


      /* dataVals.add(Entry(0f,20f))
       dataVals.add(Entry(1f,24f))
       dataVals.add(Entry(2f,28f))
       dataVals.add(Entry(3f,32f))
       dataVals.add(Entry(5f, 36F))
       dataVals.add(Entry(6f, 37F))
       dataVals.add(Entry(7f, 38F))
       dataVals.add(Entry(8f, 39F))
       dataVals.add(Entry(9f, 40F))
       dataVals.add(Entry(10f, 41F))
       dataVals.add(Entry(11f, 42F))
       dataVals.add(Entry(12f, 43F))
       dataVals.add(Entry(13f, 44F))
       dataVals.add(Entry(14f, 45F))
       dataVals.add(Entry(15f, 46F))
       dataVals.add(Entry(16f, 47F))
       dataVals.add(Entry(17f, 48F))
       dataVals.add(Entry(18f, 49F))
       dataVals.add(Entry(19f, 50F))
       dataVals.add(Entry(20f, 51F))
       dataVals.add(Entry(21f, 55F))
       dataVals.add(Entry(22f, 56F))
       dataVals.add(Entry(23f, 57F))
       dataVals.add(Entry(24f, 58F))
       dataVals.add(Entry(25f, 59F))
       dataVals.add(Entry(26f, 60F))
       dataVals.add(Entry(27f, 61F))
       dataVals.add(Entry(30f, 62F))
       dataVals.add(Entry(31f, 63F))
       dataVals.add(Entry(32f, 64F))
       dataVals.add(Entry(33f, 65F))
       dataVals.add(Entry(34f, 66F))
       dataVals.add(Entry(35f, 67F))*/
        
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