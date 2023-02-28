package com.kotlin.moneyconversionapp.data.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kotlin.moneyconversionapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitHelper {

   /* fun getRetrofitDollarSi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_DOLARSI)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }*/

    fun getBaseUrl(BASE_URL: String): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofitLogin():Retrofit{ // LOGIN
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_LOGIN)
            .addConverterFactory(ScalarsConverterFactory.create()) //important
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .build()
    }

    fun gson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

 /*   fun getRetrofitHistoricDollar(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_API_DOLLAR)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }*/

   /* fun getRetrofitLogin(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_LOGIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }*/
}