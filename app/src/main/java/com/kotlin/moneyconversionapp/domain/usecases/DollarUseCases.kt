package com.kotlin.moneyconversionapp.domain.usecases

import com.kotlin.moneyconversionapp.data.DollarRepository
import com.kotlin.moneyconversionapp.data.database.entities.toDataBase
import com.kotlin.moneyconversionapp.domain.model.DollarCasa
import javax.inject.Inject

class DollarUseCases @Inject constructor(private val repository: DollarRepository) {

    //private val repository = DollarRepository()

    //suspend operator fun invoke(): ArrayList<CasaResponse> = repository.getAllDollar()

    suspend operator fun invoke(): List<DollarCasa> {
        val dollarCasa = repository.getAllDollarFromApi()

        return if (dollarCasa.isNotEmpty()) {
            repository.deleteAllDollarCasa()
            repository.insertDollarCasa(dollarCasa.map { it.toDataBase() })
            return dollarCasa
        } else {
            repository.getAllDollarFromDataBase()
        }

    }

}