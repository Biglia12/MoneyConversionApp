package com.kotlin.moneyconversionapp.data.services

import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.LoginModel
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Services {

    @GET("api.php")
    suspend fun callApiDollar(@Query ("type")type : String): Response<ArrayList<CasaResponse>> //response es para el uso de corrutinas

    @GET("/api/evolucion/dolarblue")
    suspend fun callApiHistoricDollar(): Response<Any> //response es para el uso de corrutinas

   /* @POST("insertar.php")
    suspend fun callLogin(@Body loginModel: LoginModel): Response<Any>*/

    @FormUrlEncoded
    @POST("insertar.php")
    suspend fun callLogin(@FieldMap fields : HashMap<String,String>): Response<String>

   /* @Multipart
    @POST("insertar.php")
    suspend fun callLogin(@Body request: RequestBody): Response<Any>*/

  /*  @FormUrlEncoded
    @POST("insertar.php")
    suspend fun callLogin(@Field ("nombre") nombre: String, @Field ("email") email: String, @Field ("telefono") telefono: String, @Field ("pass") pass: String): Response<Any>*/

}