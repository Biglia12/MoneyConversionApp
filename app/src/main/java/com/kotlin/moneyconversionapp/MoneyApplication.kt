package com.kotlin.moneyconversionapp

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.gson.Gson
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.ui.view.fragments.CalculatorFragment

class MoneyApplication(val context: Context) {

    fun isConnected(): Boolean { // para la conexion a internet
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    //Store in SharedPreference
    fun setDollarValue(key: String?, value: ArrayList<CasaResponse>) {
        val prefs: SharedPreferences = context.getSharedPreferences("APP", 0)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(key, Gson().toJson(value))
        editor.apply()
    }

    //Retrieve from SharedPreference
    fun getDollarValue(key: String?): String? {
        val prefs: SharedPreferences = context.getSharedPreferences("APP", 0)
        return prefs.getString(key, "null")
    }

}



