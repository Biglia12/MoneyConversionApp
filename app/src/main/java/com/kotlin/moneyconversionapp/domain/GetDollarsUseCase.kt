package com.kotlin.moneyconversionapp.domain

import com.kotlin.moneyconversionapp.data.DollarRespository
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import javax.inject.Inject

class GetDollarsUseCase @Inject constructor(
    private val repository:DollarRespository
) {
    //private val repository = DollarRespository()
    suspend operator fun invoke (): ArrayList<CasaResponse>? =  repository.getAllDollar()

}