package com.kotlin.moneyconversionapp.data

import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.DollarCasaResponse
import com.kotlin.moneyconversionapp.data.model.DollarProvider
import com.kotlin.moneyconversionapp.data.services.DollarService
import java.util.ArrayList

class DollarRepository {

    private val api = DollarService()

    suspend fun getAllDollar(): ArrayList<CasaResponse> {
        val response = api.getDollar()
        DollarProvider.dollars = response
        return response
    }

}