package com.kotlin.moneyconversionapp.domain.usecases

import com.kotlin.moneyconversionapp.domain.Calculator
import javax.inject.Inject

class CalculatorUseCase @Inject constructor(private val calculator: Calculator) {

    //private val calculator = Calculator()

    fun calculateResult(value: String, dataValue: String): String {
        return calculator.finishResult(value, dataValue)
    }
}