package com.kotlin.moneyconversionapp

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlin.moneyconversionapp.data.model.CasaResponse
import com.kotlin.moneyconversionapp.ui.view.activities.MainActivity
import com.onesignal.OSNotification
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import org.json.JSONObject
import javax.inject.Inject

@HiltAndroidApp
class MoneyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this)//publicidad

        oneSignal()


    }

    private fun oneSignal() {
        // Inicializa OneSignal
        OneSignal.initWithContext(this)
        OneSignal.setAppId(BuildConfig.ONESIGNAL_APPID)

        // Manejar la recepción de notificaciones cuando la app está en primer plano
        OneSignal.setNotificationWillShowInForegroundHandler { notificationReceivedEvent -> // Obtener la notificación recibida
            val notification = notificationReceivedEvent?.notification
            notification?.let {

                Log.d("notificaction","recibida")

            }

        }


        // Maneja la apertura de notificaciones
        OneSignal.setNotificationOpenedHandler { result ->
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }


    fun isConnected(context: Context): Boolean { // para la conexion a internet
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
    fun setDollarValue(context: Context,key: String?, value: ArrayList<CasaResponse>) {
        val prefs: SharedPreferences = context.getSharedPreferences("APP", 0)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(key, Gson().toJson(value))
        editor.apply()
    }

    //Retrieve from SharedPreference
    fun getDollarValue(context: Context,key: String?): ArrayList<CasaResponse>? {
        val prefs: SharedPreferences = context.getSharedPreferences("APP", 0)
        val gson = Gson()
        val json = prefs.getString(key, null)
        val type = object : TypeToken<ArrayList<CasaResponse>>() {}.type
        return gson.fromJson(json, type)
    }


    class MyNotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "com.onesignal.NotificationReceivedAction") {
                val notificationData = intent.getStringExtra("data")
                try {
                    val json = JSONObject(notificationData)
                    val notificationId = json.optString("notificationId")
                    val message = json.optString("alert")
                    Log.d("OneSignal", "Notificación recibida: $message")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (intent.action == "com.onesignal.NotificationOpenedAction") {
                val notificationData = intent.getStringExtra("data")
                try {
                    val json = JSONObject(notificationData)
                    val notificationId = json.optString("notificationId")
                    val message = json.optString("alert")
                    Log.d("OneSignal", "Notificación abierta: $message")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}





