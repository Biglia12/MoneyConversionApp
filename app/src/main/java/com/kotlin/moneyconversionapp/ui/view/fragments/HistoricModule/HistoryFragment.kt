package com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotlin.moneyconversionapp.databinding.FragmentHistoryBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.Historic.HistoricDollarViewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historicDollarViewModel: HistoricDollarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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