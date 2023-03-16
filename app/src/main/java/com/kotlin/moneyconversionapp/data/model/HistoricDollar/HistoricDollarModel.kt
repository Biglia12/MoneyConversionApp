package com.kotlin.moneyconversionapp.data.model.HistoricDollar

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
class HistoricDollarModel {
    @SerializedName("date")
    @Expose
    private val date: String? = null

    @SerializedName("source")
    @Expose
    private val source: String? = null

    @SerializedName("value_sell")
    @Expose
    private val valueSell: Int? = null

    @SerializedName("value_buy")
    @Expose
    private val valueBuy: Int? = null
}