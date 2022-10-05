package com.kotlin.moneyconversionapp.data.model

class CalculateModel {

    private var result :Double = 0.0
    private var resultToString : String = ""

     fun finishResult(value: String, dataValue: String): String {

         val valueWithPoint = dataValue.replace(",", ".")

        if (value.isEmpty() || valueWithPoint.isEmpty()){
            return "0"
        }else{
            result = value.toLong() * valueWithPoint.toDouble()
            resultToString = "%.2f".format(result)
        }

        return (resultToString)

    }


}