package com.kotlin.moneyconversionapp.data.model.HistoricDollar

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
class HistoricDollarModel {
    @SerializedName("date")
    @Expose
    val date: String? = null

    @SerializedName("source")
    @Expose
    val source: String? = null

    @SerializedName("value_sell")
    @Expose
    val valueSell: Int? = null

    @SerializedName("value_buy")
    @Expose
     val valueBuy: Int? = null
}