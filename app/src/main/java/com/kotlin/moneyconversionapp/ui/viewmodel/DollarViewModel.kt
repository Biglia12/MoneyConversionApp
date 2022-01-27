package com.kotlin.moneyconversionapp.ui.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.GetDollarsUseCase
import kotlinx.coroutines.launch

class DollarViewModel : ViewModel() {

    val dollarModel = MutableLiveData<ArrayList<CasaResponse>>()
    var getDollarUseCase = GetDollarsUseCase()
    var casaResponseList = ArrayList<CasaResponse>()
    var isLoading = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            val result = getDollarUseCase()
            isLoading.postValue(true)

            if (!result.isNullOrEmpty()){
                isLoading.postValue(false)
                casaResponseList = result
                dollarModel.postValue(casaResponseList)
            }
        }

    }

}