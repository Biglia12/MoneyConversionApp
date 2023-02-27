package com.kotlin.moneyconversionapp.ui.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.UsuarioModel
import com.kotlin.moneyconversionapp.data.services.Services
import com.kotlin.moneyconversionapp.databinding.ActivityLoginAcitvitiyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAcitvitiyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAcitvitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickBtn()
    }

    private fun clickBtn() {
        binding.buttonRegister.setOnClickListener {


            val usuario = UsuarioModel(binding.editTextTextPersonName.text.toString(),binding.editTextTextEmailAddress.text.toString(),binding.editTextPhone.text.toString(), binding.editTextTextPassword.text.toString())

            val parametros = HashMap<String, String>()
            parametros.put("nombre", usuario.nombre)
            parametros.put("email", usuario.email)
            parametros.put("telefono", usuario.telefono)
            parametros.put("pass", usuario.pass)

            initRetrofit(parametros)

        }
    }

    private fun gson(): Gson {

        return GsonBuilder()
            .setLenient()
            .create()
    }

    fun initRetrofit(requestBody: HashMap<String, String>) {
        CoroutineScope(Dispatchers.IO).launch {

            val response = getRetrofit().create(Services::class.java).callLogin(requestBody)
            runOnUiThread {
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
    }


    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_LOGIN)
            .addConverterFactory(ScalarsConverterFactory.create()) //important
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}
