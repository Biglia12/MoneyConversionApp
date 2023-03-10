package com.kotlin.moneyconversionapp.ui.view.activities

import android.content.Intent
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
        observeLiveData()

    }

    private fun clickBtn() {
        binding.buttonRegister.setOnClickListener {
            when {
                binding.editTextTextPersonName.text.isNullOrEmpty() && binding.editTextTextEmailAddress.text.isNullOrEmpty() && binding.editTextPhone.text.isNullOrEmpty() && binding.editTextTextPassword.text.isNullOrEmpty() -> Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
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
                    )
                }
            }

        }
    }

    private fun observeLiveData() {
        // Pasar el LiveData a la vista (mostraremos el mensajes)
        loginViewModel.messageLiveData.observe(this as LifecycleOwner, Observer { message ->
            showToast(message)
        })


        loginViewModel.responseSuccessfull.observe(this as LifecycleOwner, Observer {responseIsSuccessful ->
            if (responseIsSuccessful == true) {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
