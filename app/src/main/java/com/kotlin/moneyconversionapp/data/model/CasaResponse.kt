package com.kotlin.moneyconversionapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CasaResponse (
    @SerializedName("casa")
    @Expose
    val  dollarCasa:  DollarCasaResponse

)
