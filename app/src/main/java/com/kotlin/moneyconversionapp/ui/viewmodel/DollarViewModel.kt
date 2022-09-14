package com.kotlin.moneyconversionapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.DollarUseCases
import kotlinx.coroutines.launch

class DollarViewModel : ViewModel() {

    val casaResponse = MutableLiveData<ArrayList<CasaResponse>>()
    val isLoading = MutableLiveData<Boolean>()
    val showError  = MutableLiveData<Boolean>()

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

            } else{
                showError.postValue(true)
                isLoading.postValue(false)
            }
        }
    }

     fun retryService(retry: Boolean) {

    }
}