package com.kotlin.moneyconversionapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.CalculateModel
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.DollarUseCases
import kotlinx.coroutines.launch

class DollarViewModel : ViewModel() {

    val casaResponse = MutableLiveData<ArrayList<CasaResponse>>()
    val casaResponseCalculator = MutableLiveData<ArrayList<CasaResponse>>()
    val isLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<Boolean>()
    val resultCalculateBuy = MutableLiveData<String>()
    val resultCalculateSell = MutableLiveData<String>()
    val calculateModel: CalculateModel = CalculateModel()

    private val getDollarUseCases = DollarUseCases()

    init {

        callService()

    }

    fun callService() {

        viewModelScope.launch {

            val result = getDollarUseCases()

            isLoading.postValue(true)

            if (!result.isNullOrEmpty()) {

                casaResponse.postValue(result)

                isLoading.postValue(false)

                showError.postValue(false)

                setSpinner(result)

            } else {
                showError.postValue(true)
                isLoading.postValue(false)
            }
        }
    }

    fun setSpinner(result: ArrayList<CasaResponse>) { // se pasa array de nombres para el spinner
        val arrayNames = arrayListOf<CasaResponse>()
        for (i in result.indices) {
            arrayNames.add(result[i])
            if (result[i].dollarCasa.nombre.toString() == "Argentina" || result[i].dollarCasa.nombre.toString() == "Dolar Soja" || result[i].dollarCasa.nombre.toString() == "Bitcoin") {
                arrayNames.remove(result[i]) // se remueve del spinner ya que no nos sirve
                //result
            }
            casaResponseCalculator.postValue(arrayNames)
        }
    }

    fun getCalculateBuy(): MutableLiveData<String> { //Obtener resultado del calculo
        return resultCalculateBuy
    }

    fun getCalculateSell(): MutableLiveData<String> { //Obtener resultado del calculo
        return resultCalculateSell
    }

    fun setCalculate (dataEditText: String, dataValue: String, valueVentaWithPoint: String){ //pasar parametros para hacer el calculo de la cuenta
          resultCalculateBuy.value = calculateModel.finishResult(dataEditText, dataValue)
          resultCalculateSell.value = calculateModel.finishResult(dataEditText, valueVentaWithPoint)
    }
}