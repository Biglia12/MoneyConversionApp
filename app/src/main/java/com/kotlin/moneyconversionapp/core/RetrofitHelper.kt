package com.kotlin.moneyconversionapp.core

import com.kotlin.moneyconversionapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*fun getRetrofitDollarSi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_DOLARSI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun getRetrofitHistoricDollar(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https:api.bluelytics.com.ar/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }*/
}