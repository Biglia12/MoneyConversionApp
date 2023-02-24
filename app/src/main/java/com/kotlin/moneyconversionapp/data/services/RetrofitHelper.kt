package com.kotlin.moneyconversionapp.data.services

import com.kotlin.moneyconversionapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getRetrofitDollarSi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_DOLARSI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun getRetrofitHistoricDollar(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_API_DOLLAR)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun getRetrofitLogin(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_LOGIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}