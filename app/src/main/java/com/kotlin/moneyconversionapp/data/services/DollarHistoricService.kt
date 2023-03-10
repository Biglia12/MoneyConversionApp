package com.kotlin.moneyconversionapp.data.services

import android.util.Log
<<<<<<< HEAD
import com.kotlin.moneyconversionapp.Constants.BASE_URL_DOLARSI
=======
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
>>>>>>> develop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class DollarHistoricService {

    private val retrofit : Retrofit = RetrofitHelper.getBaseUrl(BASE_URL_DOLARSI)

    suspend fun historicDollar () : HistoricDollarModel {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(Services::class.java).callApiHistoricDollar() // servicio sin funcionar cambiar
            Log.i("Response", response.toString())
            response.body() ?: HistoricDollarModel() // llamada en un hilo secundario para no saturar la interfaz del usuario

        }

    }

}