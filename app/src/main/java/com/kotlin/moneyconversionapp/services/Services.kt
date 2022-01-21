package com.kotlin.moneyconversionapp.services

import com.google.gson.JsonArray
import com.kotlin.moneyconversionapp.model.CasaResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    @GET("api.php")
    suspend fun callApiDollar(@Query ("type")type : String): Response<List<CasaResponse>> //response es para el uso de corrutinas

}