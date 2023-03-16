package com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.kotlin.moneyconversionapp.databinding.FragmentHistoryBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.Historic.HistoricDollarViewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historicDollarViewModel: HistoricDollarViewModel by viewModels()

    lateinit var barChart: BarChart
    private  var  barList: ArrayList<BarEntry> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barList.add(BarEntry(100f,100f))
        barList.add(BarEntry(105f,105f))
        barList.add(BarEntry(106f,106f))
        barList.add(BarEntry(107f,107f))
        barList.add(BarEntry(108f,108f))
        barList.add(BarEntry(109f,109f))
        barList.add(BarEntry(110f,110f))

        val barDataSet= BarDataSet(barList,"List")

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
        barDataSet.valueTextColor= Color.BLACK

        val barData= BarData(barDataSet)

        binding.barChart.setFitBars(true)

        binding.barChart.data= barData

        binding.barChart.description.text= "Bar Chart"

        binding.barChart.animateY(2000)

        historicDollarViewModel.loadData()

        binding.imgBtnRefresh.setOnClickListener {
            historicDollarViewModel.resetLoading()
        }

        /*historicDollarViewModel.historicDollarResponse.observe(viewLifecycleOwner, Observer {

        })*/

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