package com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardModule

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.MoneyApplication
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.ui.adapters.DashBoardModule.DashBoardAdapter
import com.kotlin.moneyconversionapp.databinding.FragmentDashBoardBinding
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.DollarResponse
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashBoardFragment @Inject constructor() : Fragment() {

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!
    private var interstitial: InterstitialAd? = null
    private lateinit var adapter: DashBoardAdapter

    private val dollarViewModel: DollarViewModel by activityViewModels()
    private val moneyApplication: MoneyApplication = MoneyApplication()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = requireActivity().resources.getString(R.string.app_name)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))

        funAdView() //funcion para publicidad
        funAdViewInter() //funcion para publicidad de Interstitial
        showAdsInterstitial()

        if (moneyApplication.isConnected(requireContext())) {
            observeLiveData()
        } else {
            //observeLiveData()
            Toast.makeText(context, "No conexion", Toast.LENGTH_SHORT).show()
        }

        swipeRefresh()//metodo para volver a llamar al servicio

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
        binding.adView.loadAd(adRequest)


        binding.adView.adListener = object : AdListener() {
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
                binding.adView.visibility = VISIBLE
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }
    }

    private fun observeLiveData() {

        dollarViewModel.casaResponse.observe(viewLifecycleOwner, Observer {
            initRecyler(it)
            moneyApplication.setDollarValue(requireContext(), Constants.DOLLAR_VALUE, it)
        })

        dollarViewModel.showRecycler.observe(viewLifecycleOwner, Observer {
            binding.recyclerResumeFragment.isVisible = it
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


    private fun initRecyler(arrayList: ArrayList<DollarResponse>) {
        adapter = DashBoardAdapter(
            arrayList,
            requireContext()
        ) //le apsamos al adapter el listado de monedas
        binding.recyclerResumeFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerResumeFragment.adapter = adapter
    }

    private fun swipeRefresh() {
        var countTap = 0
        binding.swipeDash.setOnRefreshListener {
            countTap++
            if (countTap == 2) { // Mostrar el anuncio cada dos taps
                showAds()
                countTap = 0
                funAdViewInter()
            }
            binding.recyclerResumeFragment.isVisible =
                false //lo hacemos invisble para que el usuario vea que se vuelve a recargar el lsitado de las monedas
            Handler(Looper.getMainLooper()).postDelayed({
                dollarViewModel.callService()
            }, 1000)//le asginamos un tiempo al swipe para que no sea tan rapido.

        }
    }


    fun showAds() {
       interstitial?.show(requireActivity())
    }
}

