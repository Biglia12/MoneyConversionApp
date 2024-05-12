package com.kotlin.moneyconversionapp.domain.usecases

import com.kotlin.moneyconversionapp.data.HistoricDollarRepository
import com.kotlin.moneyconversionapp.data.model.HistoricDollar.HistoricDollarModel
import javax.inject.Inject

class HistoricDollarUseCase  @Inject constructor(private val repository: HistoricDollarRepository) {

        //private val repository = HistoricDollarRepository()

        suspend operator fun invoke(): ArrayList<HistoricDollarModel> = repository.getAllDollar()


}