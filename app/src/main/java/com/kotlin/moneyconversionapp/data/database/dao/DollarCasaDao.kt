package com.kotlin.moneyconversionapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kotlin.moneyconversionapp.data.database.entities.CasaEntity

@Dao
interface DollarCasaDao {

    @Query("SELECT * FROM dollarCasa_table")
    suspend fun getAllDollarCasa(): List<CasaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(casa: List<CasaEntity>)

    @Query("DELETE FROM dollarCasa_table")
    suspend fun deleteAllDollarCasa()
}