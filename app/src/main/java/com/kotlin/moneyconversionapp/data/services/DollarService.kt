package com.kotlin.moneyconversionapp.data.services

import android.util.Log
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class DollarService {

    private val retrofit : Retrofit = RetrofitHelper.getRetrofit()

    suspend fun getDollar () : ArrayList<CasaResponse> {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(Services::class.java).callApiDollar(Constants.PARAMETER_DOLARSI)
            Log.i("Response", response.toString())
            response.body() ?: arrayListOf() // llamada en un hilo secundario para no saturar la interfaz del usuario

        }

    }

}