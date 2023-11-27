package com.kotlin.moneyconversionapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kotlin.moneyconversionapp.data.database.dao.DollarCasaDao
import com.kotlin.moneyconversionapp.data.database.entities.CasaEntity

@Database(entities = [CasaEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getCasaDao(): DollarCasaDao
}