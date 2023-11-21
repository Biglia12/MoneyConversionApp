package com.kotlin.moneyconversionapp.domain.model

import com.kotlin.moneyconversionapp.data.database.entities.CasaEntity
import com.kotlin.moneyconversionapp.data.model.CasaResponse

data class DollarCasa(
    var compra: String? = null,
    var venta: String? = null,
    var agencia: String? = null,
    var nombre: String? = null,
    var variacion: String? = null,
    var ventaCero: String? = null,
    var decimales: String? = null
)

fun CasaResponse.toDomain() = DollarCasa(
    dollarCasa.compra,
    dollarCasa.venta.toString(), dollarCasa.agencia, dollarCasa.nombre,
    dollarCasa.variacion.toString(), dollarCasa.ventaCero, dollarCasa.decimales
)

fun CasaEntity.toDomain() =
    DollarCasa(compra, venta, agencia, nombre, variacion, ventaCero, decimales)


//var compra: String? = null, var venta: Any? = null, var agencia: String? = null, var nombre: String? = null, var variacion: Any? = null, var ventaCero: String? = null, var decimales: String? = null