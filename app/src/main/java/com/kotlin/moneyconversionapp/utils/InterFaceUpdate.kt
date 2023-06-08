package com.kotlin.moneyconversionapp.utils

interface InterfaceAppUpdate {

    fun setDownloadToast(download: String)

    interface view {
        fun getDownloadToast(download: String)
    }

}