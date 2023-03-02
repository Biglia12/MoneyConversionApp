package com.kotlin.moneyconversionapp.data

import com.kotlin.moneyconversionapp.data.services.RetrofitHelper
import com.kotlin.moneyconversionapp.data.services.Services
import retrofit2.Response

class LoginRepository {

    suspend fun callLogin(parametros: HashMap<String, String>): Response<*> {
        return RetrofitHelper.getRetrofitLogin().create(Services::class.java).callLogin(parametros)
    }
}