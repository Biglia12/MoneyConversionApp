package com.kotlin.moneyconversionapp.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Para pasar la instancia de la aplicación
 * como parámetro al constructor del ViewModel,
 * se puede crear una clase fábrica como se muestra a continuación:
 *
 * En este caso, la clase fábrica recibe
 * la instancia de la aplicación como parámetro
 * y la pasa al constructor de DollarViewModel.
 * Luego, en la función create, se verifica si la clase que se quiere crear es DollarViewModel y
 * se devuelve una instancia de esta clase con la instancia de la aplicación proporcionada.

 */

class DollarViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DollarViewModel::class.java)) {
            return DollarViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}