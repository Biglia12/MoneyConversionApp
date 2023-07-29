package com.kotlin.moneyconversionapp.domain.usecases

import com.kotlin.moneyconversionapp.Constants
import javax.inject.Inject

class CalculatorUseCase @Inject constructor() {

    //private val calculator = Calculator()

    private var result: Double = 0.0
    private var resultToString: String = ""

    fun calculateResult(value: String, dataValue: String): String {

        val valueWithPoint = dataValue.replace(",", ".")

        if (value.isNullOrEmpty() || valueWithPoint.isNullOrEmpty() || valueWithPoint == "No Cotiza") {
            return dataValue
        } else {
            result = value.toLong() * valueWithPoint.toDouble()
            resultToString = Constants.PRICE_FORMAT.format(result)
        }

        return resultToString
    }
}