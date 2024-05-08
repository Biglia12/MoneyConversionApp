package com.kotlin.moneyconversionapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.DollarResponse
import com.kotlin.moneyconversionapp.domain.usecases.DashBoard.DashBoardUseCase
import com.kotlin.moneyconversionapp.domain.usecases.calculator.CalculatorUseCase
import com.kotlin.moneyconversionapp.domain.usecases.DollarUseCases
import com.kotlin.moneyconversionapp.domain.usecases.calculator.SpinnerCalculatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DollarViewModel @Inject constructor(
    private val getDollarUseCases: DollarUseCases,
    private val dashBoardUseCase: DashBoardUseCase,
    private val calculatorUseCase: CalculatorUseCase,
) : ViewModel() {

    val casaResponse :LiveData<ArrayList<DollarResponse>> get() = _casaResponse
    private val _casaResponse = MutableLiveData<ArrayList<DollarResponse>>()

    val casaResponseCalculator :LiveData<ArrayList<DollarResponse>> get() = _casaResponseCalculator
    private val _casaResponseCalculator = MutableLiveData<ArrayList<DollarResponse>>()

    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _isLoading = MutableLiveData<Boolean>()

    val showError :LiveData<Boolean> get() = _showError
    private val _showError = MutableLiveData<Boolean>()

    val showRecycler: LiveData<Boolean> get() = _showRecycler
    private val _showRecycler = MutableLiveData<Boolean>()

    val resultCalculateBuy :LiveData<String> get() = _resultCalculateBuy
    private val _resultCalculateBuy = MutableLiveData<String>()

    val resultCalculateSell : LiveData<String> get() = _resultCalculateSell
    private val _resultCalculateSell = MutableLiveData<String>()

    init {
        callService()
    }

    fun callService() {

        viewModelScope.launch {

            val result = getDollarUseCases()

            _isLoading.postValue(true)

            _showRecycler.postValue(false)

            if (!result.isNullOrEmpty()) {

                //val removeName = dashBoardUseCase.removeName(result)
                _casaResponse.postValue(result)
                //removeName(result)

                _isLoading.postValue(false)
                _showError.postValue(false)
                _showRecycler.postValue(true)

                //val arrayNamesForSpinner = calculatorUseCase.setSpinner(result)
                _casaResponseCalculator.postValue(result)
                //setSpinner(result)

                Log.d("dada", "dasdasd")


            } else {
                _showError.postValue(true)
                _isLoading.postValue(false)
            }
        }
    }



    fun setSpinnerShared(result: ArrayList<DollarResponse>) { // se pasa array de nombres para el spinner
        _casaResponseCalculator.postValue(result)
    }


    fun setCalculate(
        dataEditText: String,
        dataValue: String,
        valueVentaWithPoint: String
    ) { //pasar parametros para hacer el calculo de la cuenta
        _resultCalculateBuy.value = calculatorUseCase.calculateResult(dataEditText, dataValue)
        _resultCalculateSell.value = calculatorUseCase.calculateResult(dataEditText, valueVentaWithPoint)
    }

}