package com.kotlin.moneyconversionapp.domain.usecases.calculator

import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import javax.inject.Inject

class CalculatorUseCase @Inject constructor() {

    //private val calculator = Calculator()

    private var result: Double = 0.0
    private var resultToString: String = ""

    fun calculateResult(value: String, dataValue: String): String { //logca para calculos de la calculadora

        val valueWithPoint = dataValue.replace(",", ".")

        if (value.isNullOrEmpty() || valueWithPoint.isNullOrEmpty() || valueWithPoint == "No Cotiza") {
            return dataValue
        } else {
            result = value.toLong() * valueWithPoint.toDouble()
            resultToString = Constants.PRICE_FORMAT.format(result)
        }

        return resultToString
    }

    fun setSpinner(result: ArrayList<CasaResponse>): ArrayList<CasaResponse> {// loigca para remover bitcoin y "Aregntina" de la list
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