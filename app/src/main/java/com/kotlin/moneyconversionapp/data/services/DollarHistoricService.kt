package com.kotlin.moneyconversionapp.data.services

import android.util.Log
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class DollarHistoricService {

    private val retrofit : Retrofit = RetrofitHelper.getRetrofit(Constants.BASE_URL_HISTORIC)

    suspend fun historicDollar () : ArrayList<HistoricDollarModel> {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(Services::class.java).callApiHistoricDollar() // servicio sin funcionar cambiar
            Log.i("Response", response.toString())
            response.body() ?: ArrayList() // llamada en un hilo secundario para no saturar la interfaz del usuario

        }

    }

}