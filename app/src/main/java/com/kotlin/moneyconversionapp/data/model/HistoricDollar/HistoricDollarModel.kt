package com.kotlin.moneyconversionapp.data.model.HistoricDollar

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
class HistoricDollarModel {
    @SerializedName("fecha")
    @Expose
    private val fecha: String? = null

    @SerializedName("meses")
    @Expose
    private val meses: List<MesesModel>? = null
}