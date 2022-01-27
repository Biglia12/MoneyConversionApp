package com.kotlin.moneyconversionapp.domain

import com.kotlin.moneyconversionapp.data.DollarRespository
import com.kotlin.moneyconversionapp.data.model.CasaResponse

class GetDollarsUseCase {

    private val repository = DollarRespository()

    suspend operator fun invoke (): ArrayList<CasaResponse>? =  repository.getAllDollar()


}