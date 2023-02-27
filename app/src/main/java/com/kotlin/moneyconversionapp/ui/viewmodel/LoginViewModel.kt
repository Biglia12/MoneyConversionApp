package com.kotlin.moneyconversionapp.ui.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.services.Services
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class LoginViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()


    init {

        viewModelScope.launch {

        }
    }


    /*fun getRetrofit(): Retrofit {
        //gson()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_LOGIN)
            .addConverterFactory(ScalarsConverterFactory.create()) //important
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun initRetrofit(requestBody: HashMap<String, String>) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = getRetrofit().create(Services::class.java).callLogin(requestBody)
            if (response.isSuccessful) {
                Toast.makeText(this@LoginActivity, response.toString(), Toast.LENGTH_SHORT)
                    .show()
            } else {

                Toast.makeText(
                    this@LoginActivity,
                    response.code().toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun gson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }*/

}