package com.kotlin.moneyconversionapp.data

import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.DollarProvider
import com.kotlin.moneyconversionapp.data.model.HistoricDollarProvider
import com.kotlin.moneyconversionapp.data.services.DollarHistoricService
import com.kotlin.moneyconversionapp.data.services.DollarService
import java.util.ArrayList

class HistoricDollarRepository {

    private val api = DollarHistoricService()

    suspend fun getAllDollar(): Any {
        val response = api.historicDollar()
        HistoricDollarProvider.dollars = response
        return response
    }

}