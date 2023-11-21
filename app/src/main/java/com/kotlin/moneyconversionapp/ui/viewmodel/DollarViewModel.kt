package com.kotlin.moneyconversionapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.usecases.DashBoard.DashBoardUseCase
import com.kotlin.moneyconversionapp.domain.usecases.calculator.CalculatorUseCase
import com.kotlin.moneyconversionapp.domain.usecases.DollarUseCases
import com.kotlin.moneyconversionapp.domain.model.DollarCasa
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DollarViewModel @Inject constructor(
    private val getDollarUseCases: DollarUseCases,
    private val dashBoardUseCase: DashBoardUseCase,
    private val calculatorUseCase: CalculatorUseCase,
) : ViewModel() {

    val casaResponse = MutableLiveData<List<DollarCasa>>()
    val casaResponseCalculator = MutableLiveData<List<DollarCasa>>()
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

                val removeName = dashBoardUseCase.removeName(result)
                casaResponse.postValue(removeName)
                //removeName(result)

                isLoading.postValue(false)
                showError.postValue(false)
                showRecycler.postValue(true)

                val arrayNamesForSpinner = calculatorUseCase.setSpinner(result)
                casaResponseCalculator.postValue(arrayNamesForSpinner)
                //setSpinner(result)

                Log.d("dada", "dasdasd")


            } else {
                showError.postValue(true)
                isLoading.postValue(false)
            }
        }
    }



    fun setSpinnerShared(result: ArrayList<DollarCasa>) { // se pasa array de nombres para el spinner
        casaResponseCalculator.postValue(result)
    }


    fun setCalculate(
        dataEditText: String,
        dataValue: String,
        valueVentaWithPoint: String
    ) { //pasar parametros para hacer el calculo de la cuenta
        resultCalculateBuy.value = calculatorUseCase.calculateResult(dataEditText, dataValue)
        resultCalculateSell.value = calculatorUseCase.calculateResult(dataEditText, valueVentaWithPoint)
    }

}