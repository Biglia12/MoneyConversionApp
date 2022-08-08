package com.kotlin.moneyconversionapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.DollarUseCases
import kotlinx.coroutines.launch

class DollarViewModel : ViewModel() {

    val casaResponse = MutableLiveData<ArrayList<CasaResponse>>()

    var casaResponseList = ArrayList<CasaResponse>()

    private val getDollarUseCases = DollarUseCases()

    fun onCreate() {
        viewModelScope.launch {

            val result = getDollarUseCases()

            if (!result.isNullOrEmpty()){

                casaResponseList = result

                casaResponse.postValue(casaResponseList)

            }
        }
    }
}