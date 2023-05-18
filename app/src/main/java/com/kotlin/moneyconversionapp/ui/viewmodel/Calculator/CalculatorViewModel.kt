package com.kotlin.moneyconversionapp.ui.viewmodel.Calculator

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.moneyconversionapp.R

class CalculatorViewModel : ViewModel() {

    private val _shareIntent = MutableLiveData<Intent?>()
    val shareIntent: LiveData<Intent?>
        get() = _shareIntent

    fun generateShareIntent(view: View, priceWithDollarCompra: String, priceWithDollarVenta: String, priceBuy: String, priceSell: String) {

        val bitmap = screenShot(view)
        val pathofBmp = MediaStore.Images.Media.insertImage(
            view.context.contentResolver,
            bitmap, "title", null
        )
        val uri: Uri = Uri.parse(pathofBmp)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "DólarArg")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "$priceBuy\$$priceWithDollarCompra\n$priceSell \$$priceWithDollarVenta")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        _shareIntent.value = shareIntent
    }

    private fun screenShot(view: View): Bitmap? {
        // Invalidar la vista para forzar la actualización del sistema de caché de Android
        view.invalidate()

        // Tomar la captura de pantalla actualizada
        //val rootView: View = view.rootView
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        view.isDrawingCacheEnabled = true
        return view.drawingCache
    }
}