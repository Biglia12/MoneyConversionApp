package com.kotlin.moneyconversionapp.domain.usecases.DashBoard

import com.kotlin.moneyconversionapp.Constants
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import javax.inject.Inject

class DashBoardUseCase @Inject constructor()  {

     fun removeName(result: ArrayList<CasaResponse>): ArrayList<CasaResponse> {
        val filteredList = arrayListOf<CasaResponse>()
        for (i in result.indices) {
            if (result[i].dollarCasa.nombre.toString() != Constants.BITCOIN && result[i].dollarCasa.nombre.toString() != Constants.ARGENTINA ) {
                filteredList.add(result[i]) // se remueve de la lista ya "Argentina" que no nos sirve
            }
        }
        return filteredList
    }


}