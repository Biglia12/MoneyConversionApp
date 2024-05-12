package com.kotlin.moneyconversionapp.domain.usecases.calculator

import java.text.DecimalFormat
import javax.inject.Inject

class CalculatorUseCase @Inject constructor() {

    //private val calculator = Calculator()

    private var result: Double = 0.0
    private var resultToString: String = ""

    fun calculateResult(value: String, dataValue: String): String { //logica para calculos de la calculadora



        val cleanedValue = value.replace("[,.]".toRegex(), "")
        val cleanedDataValue = dataValue.replace("[,.]".toRegex(), "")

        if (value.isNullOrEmpty() || dataValue.isNullOrEmpty() || dataValue == "No Cotiza") {
            return dataValue
        } else {

            val valueToDouble = value.toDouble()
            val dataValueToDouble = dataValue.toDouble()

            val result: Double  = valueToDouble * dataValueToDouble
            resultToString = result.toString()

            //val valueDouble = cleanedValue.toDouble() / 100  // Dividir por 100 para ajustar la posición del decimal
            //val cleanedDataValueDouble = cleanedDataValue.toDouble(////)

            //val result = valueDouble * cleanedDataValueDouble
            //val resultString = formatNumber(result) // Formatear el resultado

            return resultToString
        }

        return resultToString
    }

    fun formatNumber(number: Double): String {
        val decimalFormat = DecimalFormat("#,##0.00")
        return decimalFormat.format(number)
    }

    //fun setSpinner(result: ArrayList<CasaResponse>): ArrayList<CasaResponse> {// loigca para remover bitcoin y "Aregntina" de la list
    //    val arrayNames = arrayListOf<CasaResponse>()
    //    for (i in result.indices) {
    //        arrayNames.add(result[i])
    //        if (result[i].dollarCasa.nombre.toString() == "Dolar Soja" || result[i].dollarCasa.nombre.toString() == "Bitcoin" || result[i].dollarCasa.nombre.toString() == "Argentina") {
    //            arrayNames.remove(result[i]) // se remueve del spinner ya que no nos sirve
    //        }
    //    }
    //    return arrayNames
    //    //casaResponseCalculator.postValue(arrayNames)
    //}

}