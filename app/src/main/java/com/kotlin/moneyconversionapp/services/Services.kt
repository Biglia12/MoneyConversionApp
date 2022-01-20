package com.kotlin.moneyconversionapp.services

import com.kotlin.moneyconversionapp.model.DollarCasaResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {

    @GET("/api/api.php?type=valoresprincipales")
    fun callApiDollar() : Call<ArrayList<DollarCasaResponse>>

}