package com.kotlin.moneyconversionapp.data

import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.DollarCasaResponse
import com.kotlin.moneyconversionapp.data.model.DollarProvider
import com.kotlin.moneyconversionapp.data.model.DollarResponse
import com.kotlin.moneyconversionapp.data.services.DollarService
import java.util.ArrayList
import javax.inject.Inject

class DollarRepository @Inject constructor(private val api: DollarService, private val dollarProvider: DollarProvider) {

    //private val api = DollarService()

    suspend fun getAllDollar(): ArrayList<DollarResponse> {
        val response = api.getDollar()
        dollarProvider.dollars = response
        return response
    }

}