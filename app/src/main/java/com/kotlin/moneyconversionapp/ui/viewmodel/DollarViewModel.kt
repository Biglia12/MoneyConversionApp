package com.kotlin.moneyconversionapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.DollarUseCases
import kotlinx.coroutines.launch

class DollarViewModel : ViewModel() {

    val casaResponse = MutableLiveData<ArrayList<CasaResponse>>()
    val array = MutableLiveData<ArrayList<String>>()
    val isLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<Boolean>()

    private val getDollarUseCases = DollarUseCases()

    init {

        callService()

    }

    fun setSpinner(result: ArrayList<CasaResponse>) {
        val arrayNames = arrayListOf<String>()
        for (i in result.indices) {
            arrayNames.add(result[i].dollarCasa.nombre.toString())

            if (result[i].dollarCasa.nombre.toString() == "Argentina") {
                arrayNames.remove("Argentina")
            }
                array.postValue(arrayNames)
        }
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

    fun retryService(retry: Boolean) {

    }
}