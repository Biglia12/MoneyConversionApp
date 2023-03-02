package com.kotlin.moneyconversionapp.data.services

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class LoginService {

    private val retrofit : Retrofit = RetrofitHelper.getRetrofitLogin()

  /*  suspend fun getLogin () : Any {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(Services::class.java).callLogin()
            Log.i("Response", response.toString())
            response.body() ?: "" // llamada en un hilo secundario para no saturar la interfaz del usuario

        }
    }*/
}