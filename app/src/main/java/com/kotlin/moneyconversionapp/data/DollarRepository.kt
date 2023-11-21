package com.kotlin.moneyconversionapp.data

import com.kotlin.moneyconversionapp.data.database.dao.DollarCasaDao
import com.kotlin.moneyconversionapp.data.database.entities.CasaEntity
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.data.services.DollarService
import com.kotlin.moneyconversionapp.domain.model.DollarCasa
import com.kotlin.moneyconversionapp.domain.model.toDomain
import javax.inject.Inject

class DollarRepository @Inject constructor(
    private val api: DollarService,
    private val dollarCasaDao: DollarCasaDao
) {

    //private val api = DollarService()

    suspend fun getAllDollarFromApi(): List<DollarCasa> {
        val response: List<CasaResponse> = api.getDollar()
        //dollarProvider.dollars = response
        //val response: ArrayList<DollarCasa> = response.map { it.toDomain() }
        return response.map { it.toDomain() }
    }

    suspend fun getAllDollarFromDataBase(): List<DollarCasa> {
        val response: List<CasaEntity> = dollarCasaDao.getAllDollarCasa()
        //dollarProvider.dollars = response
        return response.map { it.toDomain() }
    }

    suspend fun insertDollarCasa(dollarCasa: List<CasaEntity>) {
        dollarCasaDao.insertAll(dollarCasa)
    }

    suspend fun deleteAllDollarCasa() {
        dollarCasaDao.deleteAllDollarCasa()
    }

}