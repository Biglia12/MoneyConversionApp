package com.kotlin.moneyconversionapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DollarCasaResponse(

    @SerializedName("compra")
    @Expose
    val compra: String? = null,

    @SerializedName("venta")
    @Expose
    val venta: String? = null,

    @SerializedName("agencia")
    @Expose
    val agencia: String? = null,

    @SerializedName("nombre")
    @Expose
    val nombre: String? = null,

    @SerializedName("variacion")
    @Expose
    val variacion: String? = null,

    @SerializedName("ventaCero")
    @Expose
    val ventaCero: String? = null,

    @SerializedName("decimales")
    @Expose
    val decimales: String? = null

)
