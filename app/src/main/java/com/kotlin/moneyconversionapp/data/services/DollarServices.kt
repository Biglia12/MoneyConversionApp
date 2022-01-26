package com.kotlin.moneyconversionapp.data.services

import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.core.RetrofitHelper
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DollarServices {

    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getDollar(): ArrayList<CasaResponse> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(Services::class.java).callApiDollar(Constants.PARAMETER_DOLARSI)
            response.body() ?: arrayListOf()
        }
    }

}