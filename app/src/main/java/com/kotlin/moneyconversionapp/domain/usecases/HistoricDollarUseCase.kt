package com.kotlin.moneyconversionapp.domain.usecases

import com.kotlin.moneyconversionapp.data.DollarRepository
import com.kotlin.moneyconversionapp.data.HistoricDollarRepository
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel

class HistoricDollarUseCase {

        private val repository = HistoricDollarRepository()

        suspend operator fun invoke(): ArrayList<HistoricDollarModel> = repository.getAllDollar()


}