package com.kotlin.moneyconversionapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginModel {
    @SerializedName("nombre")
    @Expose
    var nombre: String? = ""

    @SerializedName("email")
    @Expose
    var email: String = ""

    @SerializedName("telefono")
    @Expose
    var telefono: String? = ""

    @SerializedName("pass")
    @Expose
    var pass: String? = ""

}