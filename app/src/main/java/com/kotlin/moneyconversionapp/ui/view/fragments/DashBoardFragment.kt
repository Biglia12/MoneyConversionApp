package com.kotlin.moneyconversionapp.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.adapters.DashBoardAdapter
import com.kotlin.moneyconversionapp.databinding.FragmentDashBoardBinding
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.services.Services
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModel
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
    private var dollarResponse = ArrayList<CasaResponse>()
    private lateinit var adapter: DashBoardAdapter
    private var position: Int = 0
    private val dollarViewModel: DollarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //callService()

        //dollarViewModel.ini

        dollarViewModel.casaResponse.observe(viewLifecycleOwner, Observer {
            initRecyler(it)
            it.forEachIndexed { index, item ->     //TODO se remueve el indice el cual se llama por el parametro "nombre" "argentina" ya que el servicio nos brinda un objeto que en este caso no nesecitamos
                if (item.dollarCasa.nombre.equals("Argentina")) {
                    adapter.removeItem(index)
                }
            }
        })

        activity?.let {
            dollarViewModel.isLoading.observe(it, Observer {
                binding.progessService.isVisible = it
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


        /*   dollarViewModel.dollarModel.observe(this, {
               initRecyler(it)
               it.forEachIndexed{index, item ->     //TODO se remueve el indice el cual se llama por el parametro "nombre" "argentina" ya que el servicio nos brinda un objeto que en este caso no nesecitamos
                   if (item.dollarCasa.nombre.equals("Argentina")) {
                       adapter.removeItem(index)
                   }
               }
           })*/

        //initRecyler(it)
    }


    private fun initRecyler(arrayList: ArrayList<CasaResponse>) {
        adapter = DashBoardAdapter(arrayList)
        binding.recyclerResumeFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerResumeFragment.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_DOLARSI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

/*    private fun callService() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Services = getRetrofit().create(Services::class.java)
            val response: Response<ArrayList<CasaResponse>> =
                call.callApiDollar(Constants.PARAMETER_DOLARSI)
            val dollar = response.body()
            activity?.runOnUiThread {
                if (response.isSuccessful && dollar != null) {
                    dollarResponse = dollar
                    initRecyler(it)
                    adapter.notifyDataSetChanged()

                    dollarResponse.forEachIndexed { index, item ->     //TODO se remueve el indice el cual se llama por el parametro "nombre" "argentina" ya que el servicio nos brinda un objeto que en este caso no nesecitamos
                        if (item.dollarCasa.nombre.equals("Argentina")) {
                            adapter.removeItem(index)
                        }
                    }

                } else {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }*/

}