package com.kotlin.moneyconversionapp.domain.usecases

import com.kotlin.moneyconversionapp.data.DollarRepository
import com.kotlin.moneyconversionapp.data.model.CasaResponse

class DollarUseCases {

    private val repository = DollarRepository()

    suspend operator fun invoke(): ArrayList<CasaResponse> = repository.getAllDollar()


}