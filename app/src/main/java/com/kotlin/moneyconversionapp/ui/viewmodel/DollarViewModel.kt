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

    var casaResponseList = ArrayList<CasaResponse>()

    private val getDollarUseCases = DollarUseCases()

    fun onCreate() {
        viewModelScope.launch {

            val result = getDollarUseCases()

            isLoading.postValue(true)

            if (!result.isNullOrEmpty()) {

                casaResponseList = result

                casaResponse.postValue(casaResponseList)

                isLoading.postValue(false)

            } else{
                isLoading.postValue(false)
            }
        }
    }
}