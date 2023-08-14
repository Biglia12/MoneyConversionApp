package com.kotlin.moneyconversionapp.ui.viewmodel.Historic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import com.kotlin.moneyconversionapp.domain.usecases.HistoricDollarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricDollarViewModel @Inject constructor(private val getDollarHistoricUseCases: HistoricDollarUseCase) : ViewModel() {

    val historicDollarLiveData = MutableLiveData<ArrayList<HistoricDollarModel>>()
   // private val getDollarHistoricUseCases = HistoricDollarUseCase()
    val loading = MutableLiveData<Boolean>()
    val graphicVisible = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()


    init {

        loadData()

    }

    fun loadData() {
        graphicVisible.postValue(false)

        viewModelScope.launch {
            try {
                loading.postValue(true)

                val result = getDollarHistoricUseCases()

                if (!result.isNullOrEmpty()) {
                    error.postValue(false)
                    loading.postValue(false)
                    graphicVisible.postValue(true)

                    result.reverse()
                    historicDollarLiveData.postValue(result)

                } else {
                    error.postValue(true)
                    loading.postValue(false)
                }
            } catch (e: Exception) {
                error.postValue(true)
                loading.postValue(false)
                Log.i("Error", e.toString())
            }
        }
    }

    fun reloadService() {
        loadData()
    }

}