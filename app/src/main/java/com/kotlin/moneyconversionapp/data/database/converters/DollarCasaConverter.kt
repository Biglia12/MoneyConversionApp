package com.kotlin.moneyconversionapp.data.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kotlin.moneyconversionapp.data.database.entities.CasaEntity

class DollarCasaConverter {
    @TypeConverter
    fun fromCasaEntity(casaEntity: CasaEntity): String {
        return Gson().toJson(casaEntity)
    }

    @TypeConverter
    fun toCasaEntity(json: String): CasaEntity {
        return Gson().fromJson(json, CasaEntity::class.java)
    }
}