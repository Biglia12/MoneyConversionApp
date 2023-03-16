package com.kotlin.moneyconversionapp.ui.viewmodel.Historic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import com.kotlin.moneyconversionapp.domain.DollarUseCases
import com.kotlin.moneyconversionapp.domain.HistoricDollarUseCase
import kotlinx.coroutines.launch

/**
 * Se utiliza una variable isLoadingData
 * para indicar si hay una corutina en ejecución.
 * Si el valor es true, significa que hay una corutina en ejecución y la
 * función no inicia una nueva.
 * De lo contrario, se inicia una nueva corutina para obtener los datos y
 * se actualiza el valor de isLoadingData en consecuencia.
 */

class HistoricDollarViewModel : ViewModel() {

    val historicDollarResponse = MutableLiveData<ArrayList<HistoricDollarModel>>()
    private val getDollarHistoricUseCases = HistoricDollarUseCase()

    private var isLoadingData = false

    init {
        loadData()
    }

    fun loadData() {
        if (!isLoadingData) {
            isLoadingData = true
            viewModelScope.launch {

                val result = getDollarHistoricUseCases()
                isLoadingData = false

                if (result != null){
                    historicDollarResponse.value = result
                }
            }
        }
    }

    fun resetLoading() {
        isLoadingData = false
        loadData()
    }

}