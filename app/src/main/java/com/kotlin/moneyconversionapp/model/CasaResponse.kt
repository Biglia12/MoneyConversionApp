package com.kotlin.moneyconversionapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CasaResponse (
    @SerializedName("casa")
    @Expose
    private val  dollarCasa:  DollarCasaResponse

)
