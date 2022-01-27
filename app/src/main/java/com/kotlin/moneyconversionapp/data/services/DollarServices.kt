package com.kotlin.moneyconversionapp.data.services

import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.core.RetrofitHelper
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DollarServices @Inject constructor(
    private val api:Services

) {
    //private val retrofit = RetrofitHelper.getRetrofit()
    suspend fun getDollar(): ArrayList<CasaResponse> {
        return withContext(Dispatchers.IO) {
            val response = api.callApiDollar(Constants.PARAMETER_DOLARSI)
            response.body() ?: arrayListOf()
        }
    }

}