package com.kotlin.moneyconversionapp.data.model

import java.io.Serializable

data class DollarResponse (
    var compra:Float?,
    var venta:Float?,
    var casa:String?,
    var nombre:String?,
    var moneda:String?,
    var fechaActulizacion:String?

):Serializable
