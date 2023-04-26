package com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardModule

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.kotlin.moneyconversionapp.BuildConfig
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.R
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
    private val dollarViewModel: DollarViewModel by activityViewModels() {
        DollarViewModelFactory(requireActivity().application)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = "DolarArg"
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))

        if (BuildConfig.FLAVOR == Constants.MONEYPROD) {
            funAdView() //funcion para publicidad
        }

        if (dollarViewModel.moneyApplication.isConnected(requireContext())){
            observeLiveData()
        }else{
            //observeLiveData()
            Toast.makeText(context,"No conexion", Toast.LENGTH_SHORT).show()
        }

        swipeRefresh()//metodo para volver a llamar al servicio

    }

    private fun funAdView() {

        MobileAds.initialize(requireActivity()) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.visibility = VISIBLE
        binding.adView.loadAd(adRequest)

        binding.adView.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }
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
        adapter = DashBoardAdapter(arrayList, requireContext()) //le apsamos al adapter el listado de monedas
        binding.recyclerResumeFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerResumeFragment.adapter = adapter
    }

    private fun swipeRefresh() {
        binding.swipeDash.setOnRefreshListener {
            binding.recyclerResumeFragment.isVisible = false //lo hacemos invisble para que el usuario vea que se vuelve a recargar el lsitado de las monedas
            Handler(Looper.getMainLooper()).postDelayed({
                dollarViewModel.callService()
            },1000)//le asginamos un tiempo al swipe para que no sea tan rapido.
        }
    }

    }

