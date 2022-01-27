package com.kotlin.moneyconversionapp.ui.viewmodel

import android.widget.FrameLayout
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.GetDollarsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DollarViewModel @Inject constructor(
    private val getDollarUseCase : GetDollarsUseCase

): ViewModel() {

    val dollarModel = MutableLiveData<ArrayList<CasaResponse>>()
   // var getDollarUseCase = GetDollarsUseCase()

    var casaResponseList = ArrayList<CasaResponse>()
    var isLoading = MutableLiveData<Boolean>()
   // var errorMessage = MutableLiveData<Boolean>()

    fun onCreate() {
        viewModelScope.launch {
            val result = getDollarUseCase()
            isLoading.postValue(true)

            if (!result.isNullOrEmpty()){
                isLoading.postValue(false)
                casaResponseList = result
                dollarModel.postValue(casaResponseList)
                //errorMessage.postValue(false)
            }else{
               // errorMessage.postValue(true)
            }
        }

    }

}