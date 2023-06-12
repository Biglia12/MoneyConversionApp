package com.kotlin.moneyconversionapp.data.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DollarProvider @Inject constructor() {
    var dollars: ArrayList<CasaResponse> = arrayListOf()
}