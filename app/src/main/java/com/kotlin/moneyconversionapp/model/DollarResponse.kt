package com.kotlin.moneyconversionapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DollarResponse(

    @SerializedName("compra")
    @Expose
    private val compra: String,

    @SerializedName("venta")
    @Expose
    private val venta: String,

    @SerializedName("agencia")
    @Expose
    private val agencia: String,

    @SerializedName("nombre")
    @Expose
    private val nombre: String,

    @SerializedName("variacion")
    @Expose
    private val variacion: String,

    @SerializedName("ventaCero")
    @Expose
    private val ventaCero: String,

    @SerializedName("decimales")
    @Expose
    private val decimales: String

)
