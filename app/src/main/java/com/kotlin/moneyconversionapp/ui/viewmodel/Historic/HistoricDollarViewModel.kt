package com.kotlin.moneyconversionapp.ui.viewmodel.Historic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import com.kotlin.moneyconversionapp.domain.HistoricDollarUseCase
import kotlinx.coroutines.cancel
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

    val historicDollarBlueLiveData = MutableLiveData<ArrayList<HistoricDollarModel>>()
    val historicDollarOficialLiveData = MutableLiveData<ArrayList<HistoricDollarModel>>()
    private val getDollarHistoricUseCases = HistoricDollarUseCase()
    val loading = MutableLiveData<Boolean>()
    val graphicVisible = MutableLiveData<Boolean>()

    private var isLoadingData = false


    private var source: String = ""
    private var date: String = ""
    private var valueSell: Int = 0
    private var valueBuy: Int = 0

    init {
        loadData()
    }

    fun loadData() {
        /*if (!isLoadingData) {
            isLoadingData = true*/
        graphicVisible.postValue(false)
            viewModelScope.launch() {
                try {
                    loading.postValue(true)

                val result = getDollarHistoricUseCases()
                //isLoadingData = false

                if (result != null) {
                    loading.postValue(false)
                    graphicVisible.postValue(true)

                    val lowArrayDollar = result

                    for (i in result.size - 1 downTo 500) { // el json nostra 7500 elementos lo que hace una gran carga para la app por lo que se dejara solo 500 elementos
                        lowArrayDollar.removeAt(i)
                    }

                    lowArrayDollar.reverse()

                    val blueDollarModels = ArrayList(lowArrayDollar.filter { it.source == "Blue" })
                    val oficialDollarModels =
                        ArrayList(lowArrayDollar.filter { it.source == "Oficial" })


                    historicDollarBlueLiveData.postValue(blueDollarModels)
                    historicDollarOficialLiveData.postValue(oficialDollarModels)

                }else{
                    loading.postValue(false)
                }
            } catch (e:Exception){
                    loading.postValue(false)
                    Log.i("Error", e.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel() // Cancel all coroutines running in this ViewModel
    }

    fun reloadService() {
        loadData()
    }

}