package com.kotlin.moneyconversionapp.domain.usecases

import com.kotlin.moneyconversionapp.domain.Calculator

class CalculatorUseCase {

    private val calculator = Calculator()

    fun calculateResult(value: String, dataValue: String): String {
        return calculator.finishResult(value, dataValue)
    }
}