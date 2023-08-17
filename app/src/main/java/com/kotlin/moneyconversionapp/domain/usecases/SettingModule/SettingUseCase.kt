package com.kotlin.moneyconversionapp.domain.usecases.SettingModule

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.graphics.drawable.DrawableCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import javax.inject.Inject

class SettingUseCase @Inject constructor() {

    fun generateQr(playStoreUrl: String): Bitmap? {
        return try {
            val multiFormatWriter = MultiFormatWriter()
            val bitMatrix = multiFormatWriter.encode(playStoreUrl, BarcodeFormat.QR_CODE, 850, 850)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)
            bitmap
        }catch (e: Exception) {
            Log.e("ExceptionQr", e.toString())
            null
        }
    }
}