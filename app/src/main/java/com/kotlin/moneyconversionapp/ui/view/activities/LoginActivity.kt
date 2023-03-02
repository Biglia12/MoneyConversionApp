package com.kotlin.moneyconversionapp.ui.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.kotlin.moneyconversionapp.databinding.ActivityLoginAcitvitiyBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.LoginViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAcitvitiyBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAcitvitiyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickBtn()

        // Pasar el LiveData a la vista (mostraremos el mensaje del servicio)
        loginViewModel.messageLiveData.observe(this as LifecycleOwner, Observer { message ->
            showToast(message)
        })

    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun clickBtn() {
        binding.buttonRegister.setOnClickListener {

            when {
                binding.editTextTextPersonName.text.isNullOrEmpty() -> Toast.makeText(this, "El campo de nombre es obligatorio", Toast.LENGTH_SHORT).show()
                binding.editTextTextEmailAddress.text.isNullOrEmpty() -> Toast.makeText(this, "El campo de email es obligatorio", Toast.LENGTH_SHORT).show()
                binding.editTextPhone.text.isNullOrEmpty() -> Toast.makeText(this, "El campo de teléfono es obligatorio", Toast.LENGTH_SHORT).show()
                binding.editTextTextPassword.text.isNullOrEmpty() -> Toast.makeText(this, "El campo de contraseña es obligatorio", Toast.LENGTH_SHORT).show()
                else -> {
                    loginViewModel.createUser(
                        binding.editTextTextPersonName.text.toString(),
                        binding.editTextTextEmailAddress.text.toString(),
                        binding.editTextPhone.text.toString(),
                        binding.editTextTextPassword.text.toString(),
                        this@LoginActivity
                    )
                }
            }

        }
    }

}

