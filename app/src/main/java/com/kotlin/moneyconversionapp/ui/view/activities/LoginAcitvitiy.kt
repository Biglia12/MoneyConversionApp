package com.kotlin.moneyconversionapp.ui.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.LoginModel
import com.kotlin.moneyconversionapp.data.services.Services
import com.kotlin.moneyconversionapp.databinding.ActivityLoginAcitvitiyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class LoginAcitvitiy : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAcitvitiyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAcitvitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickBtn()
    }

    private fun clickBtn() {
        binding.buttonRegister.setOnClickListener {

            //getRetrofit()

            val loginModel: LoginModel = LoginModel()
            loginModel.nombre = "aa"
            loginModel.email = "aa@dssd"
            loginModel.telefono = "212121"
            loginModel.pass = "12345"

            val parametros = HashMap<String, String>()
            parametros.put("nombre", "nanana")
            parametros.put("email", "dasdasd")
            parametros.put("telefono", "6")
            parametros.put("pass", "44545")

            initRetrofit(parametros)
            // Toast.makeText(this,"eddasdsada",Toast.LENGTH_LONG).show()
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
                    Toast.makeText(this@LoginAcitvitiy, response.toString(), Toast.LENGTH_SHORT)
                        .show()
                } else {

                    Toast.makeText(
                        this@LoginAcitvitiy,
                        response.code().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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

}
