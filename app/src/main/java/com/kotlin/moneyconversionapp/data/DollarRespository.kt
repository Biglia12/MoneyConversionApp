package com.kotlin.moneyconversionapp.data

import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.DollarProvider
import com.kotlin.moneyconversionapp.data.services.DollarServices

class DollarRespository {

    private val api = DollarServices()

    suspend fun getAllDollar(): ArrayList<CasaResponse>{
        val response = api.getDollar()
        DollarProvider.dollars = response
        return response
    }

}