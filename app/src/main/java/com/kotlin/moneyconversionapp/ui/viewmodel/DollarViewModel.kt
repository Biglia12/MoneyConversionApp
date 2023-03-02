package com.kotlin.moneyconversionapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.domain.Calculator
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.CalculatorUseCase
import com.kotlin.moneyconversionapp.domain.DollarUseCases
import kotlinx.coroutines.launch

class DollarViewModel : ViewModel() {

    val casaResponse = MutableLiveData<ArrayList<CasaResponse>>()
    val casaResponseCalculator = MutableLiveData<ArrayList<CasaResponse>>()
    val isLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<Boolean>()
    val resultCalculateBuy = MutableLiveData<String>()
    val resultCalculateSell = MutableLiveData<String>()
    val calculator: Calculator = Calculator()

    private val getDollarUseCases = DollarUseCases()

    private val calculatorUseCase = CalculatorUseCase()

    init {

        callService()

    }

    fun callService() {

        viewModelScope.launch {

            val result = getDollarUseCases()

            isLoading.postValue(true)

            if (!result.isNullOrEmpty()) {

                removeName(result)

                isLoading.postValue(false)

                showError.postValue(false)

                setSpinner(result)

            } else {
                showError.postValue(true)
                isLoading.postValue(false)
            }
        }
    }

    private fun removeName(result: ArrayList<CasaResponse>) {
        for (i in result.indices) {
            if (result[i].dollarCasa.nombre.toString() == "Argentina") {
                result.remove(result[i]) // se remueve de la lista ya "Argentina" que no nos sirve
            }
            casaResponse.postValue(result)
        }
    }

    fun setSpinner(result: ArrayList<CasaResponse>) { // se pasa array de nombres para el spinner
        val arrayNames = arrayListOf<CasaResponse>()
        for (i in result.indices) {
            arrayNames.add(result[i])
            if (result[i].dollarCasa.nombre.toString() == "Dolar Soja" || result[i].dollarCasa.nombre.toString() == "Bitcoin") {
                arrayNames.remove(result[i]) // se remueve del spinner ya que no nos sirve
            }
        }
        casaResponseCalculator.postValue(arrayNames)
    }


    fun setCalculate (dataEditText: String, dataValue: String, valueVentaWithPoint: String){ //pasar parametros para hacer el calculo de la cuenta
        resultCalculateBuy.value = calculatorUseCase.calculateResult(dataEditText, dataValue)
        resultCalculateSell.value = calculatorUseCase.calculateResult(dataEditText, valueVentaWithPoint)
    }
}