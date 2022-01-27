package com.kotlin.moneyconversionapp.data

import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.DollarProvider
import com.kotlin.moneyconversionapp.data.services.DollarServices
import javax.inject.Inject

class DollarRespository @Inject constructor(
    private val api : DollarServices,
    private val dollarProvider: DollarProvider
) {

    //private val api = DollarServices()

    suspend fun getAllDollar(): ArrayList<CasaResponse>{
        val response = api.getDollar()
        dollarProvider.dollars = response
        return response
    }

}