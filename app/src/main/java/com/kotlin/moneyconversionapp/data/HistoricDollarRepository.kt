package com.kotlin.moneyconversionapp.data

import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarProvider
import com.kotlin.moneyconversionapp.data.services.DollarHistoricService
import com.kotlin.moneyconversionapp.domain.usecases.HistoricDollarUseCase
import javax.inject.Inject

class HistoricDollarRepository @Inject constructor(private val api: DollarHistoricService, private val historicDollarProvider: HistoricDollarProvider) {

    //private val api = DollarHistoricService()

    suspend fun getAllDollar(): ArrayList<HistoricDollarModel> {
        val response = api.historicDollar()
        historicDollarProvider.dollars = response
        return response
    }

}