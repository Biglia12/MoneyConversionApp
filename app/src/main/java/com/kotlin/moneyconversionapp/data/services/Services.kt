package com.kotlin.moneyconversionapp.data.services

import com.kotlin.moneyconversionapp.data.model.CasaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    @GET("api.php")
    suspend fun callApiDollar(@Query ("type")type : String): Response<ArrayList<CasaResponse>> //response es para el uso de corrutinas

}