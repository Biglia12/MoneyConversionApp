package com.kotlin.moneyconversionapp.data.services

import android.util.Log
import com.kotlin.moneyconversionapp.Constants.BASE_URL_DOLARSI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class DollarHistoricService {

    private val retrofit : Retrofit = RetrofitHelper.getBaseUrl(BASE_URL_DOLARSI)

    suspend fun historicDollar () : Any {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(Services::class.java).callApiHistoricDollar()
            Log.i("Response", response.toString())
            response.body() ?: "" // llamada en un hilo secundario para no saturar la interfaz del usuario

        }

    }

}