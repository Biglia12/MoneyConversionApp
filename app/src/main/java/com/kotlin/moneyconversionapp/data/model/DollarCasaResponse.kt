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
    var variacion: Any? = null,  //lo tuvimos que pasar a Any ya que a veces el servicio nos devuelve  otro tipo de valor y no un string como esperabamos

    @SerializedName("ventaCero")
    @Expose
    var ventaCero: String? = null,

    @SerializedName("decimales")
    @Expose
    var decimales: String? = null

)
