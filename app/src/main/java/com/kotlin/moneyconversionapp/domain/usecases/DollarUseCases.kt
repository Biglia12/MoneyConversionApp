package com.kotlin.moneyconversionapp.domain.usecases

import com.kotlin.moneyconversionapp.data.DollarRepository
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import javax.inject.Inject

class DollarUseCases @Inject constructor(private val repository: DollarRepository) {

    //private val repository = DollarRepository()

    suspend operator fun invoke(): ArrayList<CasaResponse> = repository.getAllDollar()


}