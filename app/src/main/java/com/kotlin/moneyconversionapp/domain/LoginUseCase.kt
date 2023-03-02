package com.kotlin.moneyconversionapp.domain

import com.kotlin.moneyconversionapp.data.LoginRepository
import com.kotlin.moneyconversionapp.data.model.UsuarioModel
import retrofit2.Response

class LoginUseCase() {

    private val loginRepository = LoginRepository()

    suspend fun callLogin(usuario: UsuarioModel): Response<*> {
        val parametros = HashMap<String, String>()
        parametros.put("nombre", usuario.nombre)
        parametros.put("email", usuario.email)
        parametros.put("telefono", usuario.telefono)
        parametros.put("pass", usuario.pass)
        return loginRepository.callLogin(parametros)
    }

}
