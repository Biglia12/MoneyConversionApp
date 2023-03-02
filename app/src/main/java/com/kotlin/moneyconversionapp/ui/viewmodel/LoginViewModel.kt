package com.kotlin.moneyconversionapp.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.moneyconversionapp.data.model.UsuarioModel
import com.kotlin.moneyconversionapp.data.services.RetrofitHelper
import com.kotlin.moneyconversionapp.data.services.Services
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val messageLiveData = MutableLiveData<String>()

    fun createUser(name: String, email: String, telefono: String, pass: String, activity: Activity) {

        val usuario = UsuarioModel(name, email, telefono, pass)

        val parametros = HashMap<String, String>()
        parametros.put("nombre", usuario.nombre)
        parametros.put("email", usuario.email)
        parametros.put("telefono", usuario.telefono)
        parametros.put("pass", usuario.pass)

        initRetrofit(parametros, activity)


    }

    fun initRetrofit(requestBody: HashMap<String, String>, activity: Activity) {
        viewModelScope.launch {
            val response = RetrofitHelper.getRetrofitLogin().create(Services::class.java)
                .callLogin(requestBody)
            activity.runOnUiThread {
                if (response.isSuccessful) {
                    messageLiveData.value = response.toString()
                } else {
                    messageLiveData.value = response.code().toString()
                }
            }
        }
    }

}