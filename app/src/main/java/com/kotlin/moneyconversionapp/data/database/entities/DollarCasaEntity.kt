package com.kotlin.moneyconversionapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity
data class DollarCasaEntity (
    val compra: Double,
    val venta: Double,
    val agencia: Int,
    val nombre: String,
    val variacion: Double,
    val ventaCero: Boolean,
    val decimales: Int
)