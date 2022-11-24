package com.kotlin.moneyconversionapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.DollarUseCases
import com.kotlin.moneyconversionapp.domain.HistoricDollarUseCase
import kotlinx.coroutines.launch

class HistoricDollarViewModel : ViewModel() {

    val historicDollarResponse = MutableLiveData<Any>()
    private val getDollarUseCases = HistoricDollarUseCase()

    init {

        callService()

    }

    private fun callService() {
        viewModelScope.launch {// por el momento lo dejamos comentado ya que el servicio no esta funcionando si sigue asi se sacara
            val result = getDollarUseCases()

            result

        }
    }


}