package com.kotlin.moneyconversionapp.data.services

import android.util.Log
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.core.RetrofitHelper
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

class DollarService @Inject constructor(private val api: Services) {

    //private val retrofit : Retrofit = RetrofitHelper.getRetrofit(Constants.BASE_URL_DOLARSI)
    //https://api.bluelytics.com.ar/v2/evolution.json

    suspend fun getDollar () : ArrayList<CasaResponse> {
        return withContext(Dispatchers.IO){
            var response : Response<ArrayList<CasaResponse>>? = null
            try {
                response = api.callApiDollar(Constants.PARAMETER_DOLARSI)
                Log.i("Response", response.toString())
                response.body() ?: arrayListOf()
            }catch (e:Exception){
                response?.body() ?: arrayListOf()
            }
         /*   val response = retrofit.create(Services::class.java).callApiDollar(Constants.PARAMETER_DOLARSI)
            Log.i("Response", response.toString())
            response.body() ?: arrayListOf()*/ // llamada en un hilo secundario para no saturar la interfaz del usuario

        }

    }

}