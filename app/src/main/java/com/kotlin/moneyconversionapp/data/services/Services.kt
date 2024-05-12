package com.kotlin.moneyconversionapp.data.services

import com.kotlin.moneyconversionapp.data.model.DollarResponse
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import retrofit2.Response
import retrofit2.http.GET

interface Services {

//    @GET("api.php")
//    suspend fun callApiDollar(@Query("type") type: String): Response<ArrayList<CasaResponse>> //response es para el uso de corrutinas

    /*@GET("/api/evolucion/dolarblue")
    suspend fun callApiHistoricDollar(): Response<HistoricDollarModel> *///response es para el uso de corrutinas

    @GET("v1/dolares")
    suspend fun callDollarApi(): Response<ArrayList<DollarResponse>>

    //https:api.bluelytics.com.ar/v2/evolution.json
    @GET("evolution.json")
    suspend fun callApiHistoricDollar(): Response<ArrayList<HistoricDollarModel>>

}