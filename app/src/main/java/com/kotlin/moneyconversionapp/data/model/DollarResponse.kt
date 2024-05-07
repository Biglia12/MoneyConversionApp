package com.kotlin.moneyconversionapp.data.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale


data class DollarResponse(
    var compra: Float?,
    var venta: Float?,
    var casa: String?,
    var nombre: String?,
    var moneda: String?,
    var fechaActualizacion: String?

) : Serializable {

    fun formatDate(): String? {
        if (!this.fechaActualizacion!!.isNullOrEmpty()) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val date = inputFormat.parse(this.fechaActualizacion)
            return outputFormat.format(date)
        }
        return ""
    }
}






