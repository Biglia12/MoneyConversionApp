package com.kotlin.moneyconversionapp.domain

import com.kotlin.moneyconversionapp.data.DollarRepository
import com.kotlin.moneyconversionapp.data.HistoricDollarRepository
import com.kotlin.moneyconversionapp.data.model.CasaResponse

class HistoricDollarUseCase {

        private val repository = HistoricDollarRepository()

        suspend operator fun invoke(): Any = repository.getAllDollar()


}