package com.kotlin.moneyconversionapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.adapters.DashBoardAdapter
import com.kotlin.moneyconversionapp.databinding.FragmentDashBoardBinding
import com.kotlin.moneyconversionapp.model.CasaResponse
import com.kotlin.moneyconversionapp.model.DollarCasaResponse
import com.kotlin.moneyconversionapp.services.Services
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashBoardFragment : Fragment() {

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!
    //private var dollarResponse = listOf<DollarCasaResponse>()
    private var dollarResponse = listOf<CasaResponse>()
    private lateinit var adapter : DashBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callService()
        initRecyler()
    }


    private fun initRecyler() {
        adapter = DashBoardAdapter(dollarResponse)
        binding.recyclerResumeFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerResumeFragment.adapter = adapter

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_DOLARSI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private fun callService() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Services = getRetrofit().create(Services::class.java)
            val response: Response<List<CasaResponse>> = call.callApiDollar("valoresprincipales")
            val dollar = response.body()!!
            activity?.runOnUiThread {
                if (response.isSuccessful && response.body() != null) {
                    dollarResponse= dollar
                    adapter.notifyDataSetChanged()
                  initRecyler()
                } else {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }

}
