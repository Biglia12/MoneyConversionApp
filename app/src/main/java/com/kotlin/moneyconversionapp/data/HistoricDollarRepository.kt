package com.kotlin.moneyconversionapp.data

import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarProvider
import com.kotlin.moneyconversionapp.data.services.DollarHistoricService

class HistoricDollarRepository {

    private val api = DollarHistoricService()

    suspend fun getAllDollar(): HistoricDollarModel {
        val response = api.historicDollar()
        HistoricDollarProvider.dollars = response
        return response
    }

}