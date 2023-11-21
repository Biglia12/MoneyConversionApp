package com.kotlin.moneyconversionapp.domain.usecases.DashBoard

import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.domain.model.DollarCasa
import javax.inject.Inject

class DashBoardUseCase @Inject constructor()  {

     fun removeName(result: List<DollarCasa>): List<DollarCasa> {
        val filteredList = arrayListOf<DollarCasa>()
        for (i in result.indices) {
            if (result[i].nombre.toString() != Constants.BITCOIN && result[i].nombre.toString() != Constants.ARGENTINA ) {
                filteredList.add(result[i]) // se remueve de la lista ya "Argentina" que no nos sirve
            }
        }
        return filteredList
    }


}