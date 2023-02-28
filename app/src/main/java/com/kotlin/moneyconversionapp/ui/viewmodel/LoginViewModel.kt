package com.kotlin.moneyconversionapp.ui.viewmodel

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.UsuarioModel
import com.kotlin.moneyconversionapp.data.services.Services
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class LoginViewModel : ViewModel() {


    fun createUser(name: String,email: String,telefono: String,pass: String, activity: Activity) {

        val usuario = UsuarioModel(name, email, telefono, pass)

        val parametros = HashMap<String, String>()
        parametros.put("nombre", usuario.nombre)
        parametros.put("email", usuario.email)
        parametros.put("telefono", usuario.telefono)
        parametros.put("pass", usuario.pass)

        initRetrofit(parametros, activity)

    }


    fun getRetrofit(): Retrofit {
        //gson()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_LOGIN)
            .addConverterFactory(ScalarsConverterFactory.create()) //important
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun initRetrofit(requestBody: HashMap<String, String>, activity: Activity) {
            viewModelScope.launch {
            val response = getRetrofit().create(Services::class.java).callLogin(requestBody)
            activity.runOnUiThread {
                if (response.isSuccessful) {
                    showToast(activity, response.toString())
                } else {
                    showToast(activity, response.code().toString())
                }
            }
        }
    }

    fun gson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    fun showToast(activity: Activity, message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


}