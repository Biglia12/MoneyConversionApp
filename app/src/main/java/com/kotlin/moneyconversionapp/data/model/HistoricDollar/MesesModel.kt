package com.kotlin.moneyconversionapp.data.model.HistoricDollar

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
class MesesModel {

    @SerializedName("anio")
    @Expose
    private val anio: String? = null

    @SerializedName("mes")
    @Expose
    private val mes: String? = null

    @SerializedName("valor")
    @Expose
    private val valor: String? = null

}