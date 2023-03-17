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
    val historicDollarBlueLiveData = MutableLiveData<ArrayList<HistoricDollarModel>>()
    val historicDollarOficialLiveData = MutableLiveData<ArrayList<HistoricDollarModel>>()
    val dateLiveData = MutableLiveData<String>()
    val valueBuyLiveData = MutableLiveData<Int>()
    val sourceLiveData = MutableLiveData<String>()
    private val getDollarHistoricUseCases = HistoricDollarUseCase()

    private var isLoadingData = false

    private var source: String = ""
    private var date: String = ""
    private var valueSell: Int = 0
    private var valueBuy: Int = 0

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

                    val lowArrayDollar = result

                    for (i in result.size - 1 downTo 500){ // el json nostra 7500 elementos lo que hace una gran carga para la app por lo que se dejara solo 500 elementos
                        lowArrayDollar.removeAt(i)
                    }


                    val blueDollarModels = ArrayList(lowArrayDollar.filter { it.source == "Blue" })
                    val oficialDollarModels = ArrayList(lowArrayDollar.filter { it.source == "Oficial" })


                    historicDollarBlueLiveData.postValue(blueDollarModels)
                    historicDollarOficialLiveData.postValue(oficialDollarModels)

                   /* val blueDollarValues = blueDollarModels.map { it.valueSell } // Crear un array con los valores de "valueSell"
                    historicDollarBlueLiveData.postValue(blueDollarValues as ArrayList<Int>)
                    //val dateDollarBlueDate

                    val oficialDollarValues = oficialDollarModels.map { it.valueSell } // Crear un array con los valores de "valueSell"
                    historicDollarOficialLiveData.postValue(oficialDollarValues as ArrayList<Int>)*/


                    //historicDollarResponse.value = result
                    //historicDollarResponse.postValue(result)
                }
            }
        }
    }

    fun resetLoading() {
        isLoadingData = false
        loadData()
    }

}