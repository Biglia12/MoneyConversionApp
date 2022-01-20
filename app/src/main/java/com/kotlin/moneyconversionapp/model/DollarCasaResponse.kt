package com.kotlin.moneyconversionapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DollarCasaResponse(
    @SerializedName("casa")
    @Expose
    private val  casa: ArrayList<DollarResponse>

)
