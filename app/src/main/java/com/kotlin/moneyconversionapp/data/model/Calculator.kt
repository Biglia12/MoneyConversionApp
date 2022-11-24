package com.kotlin.moneyconversionapp.data.model

class Calculator {

    private var result :Double = 0.0
    private var resultToString : String = ""

     fun finishResult(value: String, dataValue: String): String {

         val valueWithPoint = dataValue.replace(",", ".")

        if (value.isNullOrEmpty() || valueWithPoint.isNullOrEmpty() || valueWithPoint == "No Cotiza"){
            return dataValue
        }else{
            result = value.toLong() * valueWithPoint.toDouble()
            resultToString = "%.2f".format(result)
        }

        return (resultToString)

    }


}