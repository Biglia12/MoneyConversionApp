package com.kotlin.moneyconversionapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DollarCasaResponse(

    @SerializedName("compra")
    @Expose
    var compra: String? = null,

    @SerializedName("venta")
    @Expose
    var venta: String? = null,

    @SerializedName("agencia")
    @Expose
    var agencia: String? = null,

    @SerializedName("nombre")
    @Expose
    var nombre: String? = null,

    @SerializedName("variacion")
    @Expose
    val variacion: String? = null,

    @SerializedName("ventaCero")
    @Expose
    var ventaCero: String? = null,

    @SerializedName("decimales")
    @Expose
    var decimales: String? = null

)
