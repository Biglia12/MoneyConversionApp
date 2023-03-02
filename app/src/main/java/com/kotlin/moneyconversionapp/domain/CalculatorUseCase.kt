package com.kotlin.moneyconversionapp.domain

class CalculatorUseCase {

    private val calculator = Calculator()

    fun calculateResult(value: String, dataValue: String): String {
        return calculator.finishResult(value, dataValue)
    }
}