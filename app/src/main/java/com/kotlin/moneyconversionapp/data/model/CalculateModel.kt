package com.kotlin.moneyconversionapp.data.model

class CalculateModel {

    private var result :Double = 0.0
    private var resultToString : String = ""

     fun finishResult(value: String, dataValue: String): String {

        if (value.isEmpty() || dataValue.isEmpty()){
            return "0"
        }else{
            result = value.toLong() * dataValue.toDouble()
            resultToString = result.toString()
        }

        return resultToString

    }


}