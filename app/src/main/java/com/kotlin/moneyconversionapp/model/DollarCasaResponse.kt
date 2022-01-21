package com.kotlin.moneyconversionapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DollarCasaResponse(

    @SerializedName("compra")
    @Expose
    private val compra: String? = null,

    @SerializedName("venta")
    @Expose
    private val venta: String? = null,

    @SerializedName("agencia")
    @Expose
    private val agencia: String? = null,

    @SerializedName("nombre")
    @Expose
    private val nombre: String? = null,

    @SerializedName("variacion")
    @Expose
    private val variacion: String? = null,

    @SerializedName("ventaCero")
    @Expose
    private val ventaCero: String? = null,

    @SerializedName("decimales")
    @Expose
    private val decimales: String? = null

)
