package com.kotlin.moneyconversionapp.data.services

import android.util.Log
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.core.RetrofitHelper
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class DollarHistoricService @Inject constructor(@Named("baseurlHistoric") private val api: Services) {

    // private val retrofit : Retrofit = RetrofitHelper.getRetrofit(Constants.BASE_URL_HISTORIC)

    suspend fun historicDollar(): ArrayList<HistoricDollarModel> {
        return withContext(Dispatchers.IO) {
            val response = api.callApiHistoricDollar() // servicio sin funcionar cambiar
            Log.i("Response", response.toString())
            response.body()
                ?: ArrayList() // llamada en un hilo secundario para no saturar la interfaz del usuario

        }

    }

}