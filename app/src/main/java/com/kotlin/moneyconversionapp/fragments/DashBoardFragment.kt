package com.kotlin.moneyconversionapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.kotlin.moneyconversionapp.databinding.FragmentDashBoardBinding
import com.kotlin.moneyconversionapp.model.CasaResponse
import com.kotlin.moneyconversionapp.services.Services
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashBoardFragment : Fragment() {

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRetrofit()
        callService()
       // initRecyler()
    }


    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.dolarsi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private fun callService() {
        //Toast.makeText(activity, "Its toast!", Toast.LENGTH_SHORT).show()
        /*val services = getRetrofit().create(Services :: class.java).callApiDollar("valoresprincipales")
            services.enqueue( object : Callback<List<CasaResponse>>{
                override fun onResponse(
                    call: Call<List<CasaResponse>>,
                    response: Response <List<CasaResponse>>
                ) {
                    if (response.isSuccessful){

                    }
                }

                override fun onFailure(call: Call<List<CasaResponse>>, t: Throwable) {
                    Toast.makeText(activity, "Its toast!", Toast.LENGTH_SHORT).show()
                }

            })*/

        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(Services::class.java).callApiDollar("valoresprincipales")
            val dollar = call.body()
            if (call.isSuccessful){

            }else{

            }
        }

    }

    private fun initRecyler() {

       /* val dollarResponse: List<DollarResponse> = listOf(
            DollarResponse("pikachu", "truen"),
            DollarResponse("bulbazoor", "agua")
        )*/
        binding.recyclerResumeFragment.apply {
            layoutManager = LinearLayoutManager(context)
            //adapter = DashBoardAdapter(dollarResponse)

        }

    }



}
