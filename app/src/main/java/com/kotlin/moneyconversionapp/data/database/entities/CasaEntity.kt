package com.kotlin.moneyconversionapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlin.moneyconversionapp.domain.model.DollarCasa

@Entity(tableName = "dollarCasa_table")
data class CasaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "compra") val compra: String?,
    @ColumnInfo(name = "venta") val venta: String?,
    @ColumnInfo(name = "agencia") val agencia: String?,
    @ColumnInfo(name = "nombre") val nombre: String?,
    @ColumnInfo(name = "variacion") val variacion: String?,
    @ColumnInfo(name = "ventaCero") val ventaCero: String?,
    @ColumnInfo(name = "decimales") val decimales: String?
   // @Embedded(prefix = "dollarCasa") val dollarCasa: DollarCasaResponse
)


fun DollarCasa.toDataBase() = CasaEntity(compra = compra, venta = venta as String?, agencia = agencia, nombre = nombre, variacion = variacion as String?, ventaCero = ventaCero, decimales = decimales)