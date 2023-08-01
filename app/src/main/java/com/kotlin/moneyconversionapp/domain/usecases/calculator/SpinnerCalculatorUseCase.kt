package com.kotlin.moneyconversionapp.domain.usecases.calculator

import com.kotlin.moneyconversionapp.data.model.CasaResponse
import javax.inject.Inject

class SpinnerCalculatorUseCase  @Inject constructor() {

    fun setSpinner(result: ArrayList<CasaResponse>): ArrayList<CasaResponse> {
        val arrayNames = arrayListOf<CasaResponse>()
        for (i in result.indices) {
            arrayNames.add(result[i])
            if (result[i].dollarCasa.nombre.toString() == "Dolar Soja" || result[i].dollarCasa.nombre.toString() == "Bitcoin") {
                arrayNames.remove(result[i]) // se remueve del spinner ya que no nos sirve
            }
        }
        return arrayNames
        //casaResponseCalculator.postValue(arrayNames)
    }



}