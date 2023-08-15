package com.kotlin.moneyconversionapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.usecases.calculator.CalculatorUseCase
import com.kotlin.moneyconversionapp.domain.usecases.DollarUseCases
import com.kotlin.moneyconversionapp.domain.usecases.calculator.SpinnerCalculatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DollarViewModel @Inject constructor(
    private val getDollarUseCases: DollarUseCases,
    private val calculatorUseCase: CalculatorUseCase,
    private val spinnerCalculatorUseCase: SpinnerCalculatorUseCase
) : ViewModel() {

    val casaResponse = MutableLiveData<ArrayList<CasaResponse>>()
    val casaResponseShared = MutableLiveData<ArrayList<CasaResponse>>()
    val casaResponseCalculator = MutableLiveData<ArrayList<CasaResponse>>()
    val isLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<Boolean>()
    val showRecycler = MutableLiveData<Boolean>()
    val resultCalculateBuy = MutableLiveData<String>()
    val resultCalculateSell = MutableLiveData<String>()


    init {
        callService()
    }

    fun callService() {

        viewModelScope.launch {

            val result = getDollarUseCases()

            isLoading.postValue(true)

            showRecycler.postValue(false)

            if (!result.isNullOrEmpty()) {
                removeName(result)
                isLoading.postValue(false)
                showError.postValue(false)
                showRecycler.postValue(true)

                val arrayNamesForSpinner = spinnerCalculatorUseCase.setSpinner(result)
                casaResponseCalculator.postValue(arrayNamesForSpinner)
                //setSpinner(result)
                casaResponseShared.postValue(result)

                Log.d("dada", "dasdasd")


            } else {
                showError.postValue(true)
                isLoading.postValue(false)
            }
        }
    }

    private fun removeName(result: ArrayList<CasaResponse>) {
        val filteredList = arrayListOf<CasaResponse>()
        for (i in result.indices) {
            if (result[i].dollarCasa.nombre.toString() != Constants.BITCOIN && result[i].dollarCasa.nombre.toString() != Constants.ARGENTINA ) {
                filteredList.add(result[i]) // se remueve de la lista ya "Argentina" que no nos sirve
            }
        }
        casaResponse.postValue(filteredList)
    }

    fun setSpinner(result: ArrayList<CasaResponse>) { // se pasa array de nombres para el spinner
        val arrayNames = arrayListOf<CasaResponse>()
        for (i in result.indices) {
            arrayNames.add(result[i])
            if (result[i].dollarCasa.nombre.toString() == Constants.DOLLAR_SOJA || result[i].dollarCasa.nombre.toString() == Constants.BITCOIN) {
                arrayNames.remove(result[i]) // se remueve del spinner ya que no nos sirve
            }
        }
        casaResponseCalculator.postValue(arrayNames)
    }


    fun setCalculate(
        dataEditText: String,
        dataValue: String,
        valueVentaWithPoint: String
    ) { //pasar parametros para hacer el calculo de la cuenta
        resultCalculateBuy.value = calculatorUseCase.calculateResult(dataEditText, dataValue)
        resultCalculateSell.value =
            calculatorUseCase.calculateResult(dataEditText, valueVentaWithPoint)
    }

}