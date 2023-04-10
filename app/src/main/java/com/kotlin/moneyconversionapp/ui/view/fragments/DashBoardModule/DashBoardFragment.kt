package com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardModule

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.moneyconversionapp.adapters.DashBoardAdapter
import com.kotlin.moneyconversionapp.databinding.FragmentDashBoardBinding
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModel
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModelFactory

class DashBoardFragment : Fragment() {

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!

    //private var dollarResponse = listOf<DollarCasaResponse>()
    private var dollarResponse = ArrayList<CasaResponse>()
    private lateinit var adapter: DashBoardAdapter
    private var position: Int = 0
    private val dollarViewModel: DollarViewModel by viewModels {
        DollarViewModelFactory(requireActivity().application)
    }
   // private val moneyApplication: MoneyApplication = MoneyApplication(requireActivity())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //dollarViewModel.callService()

        //dollarViewModel.ini

        if (dollarViewModel.moneyApplication.isConnected()){
            observeLiveData()
        }else{
            //observeLiveData()
            Toast.makeText(context,"No conexion", Toast.LENGTH_SHORT).show()
        }

        swipeRefresh()

    }

    private fun observeLiveData() {

        dollarViewModel.showRecycler.observe(viewLifecycleOwner, Observer {
            binding.recyclerResumeFragment.isVisible = it
        })

        dollarViewModel.casaResponse.observe(viewLifecycleOwner, Observer {
            initRecyler(it)
        })

        activity?.let {
            dollarViewModel.isLoading.observe(it, Observer {
                binding.progessService.isVisible = it
                binding.swipeDash.isRefreshing = it
            })
        }

        activity?.let {
            dollarViewModel.showError.observe(it, Observer {
                binding.constraintErrorService.isVisible = it
            })
        }

        activity.let {
            binding.retryErrorButton.setOnClickListener {
                binding.constraintErrorService.isVisible = false
                binding.progessService.isVisible = true
                dollarViewModel.callService()
            }
        }
    }


    private fun initRecyler(arrayList: ArrayList<CasaResponse>) {
        adapter = DashBoardAdapter(arrayList, requireContext())
        binding.recyclerResumeFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerResumeFragment.adapter = adapter
    }

    private fun swipeRefresh() {
        binding.swipeDash.setOnRefreshListener {
            binding.recyclerResumeFragment.isVisible = false
            Handler(Looper.getMainLooper()).postDelayed({
                dollarViewModel.callService()
            },1000)
        }
    }




    }

