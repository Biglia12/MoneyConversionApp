package com.kotlin.moneyconversionapp.ui.viewmodel.Setting

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.moneyconversionapp.domain.usecases.SettingModule.SettingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingUseCase: SettingUseCase) : ViewModel() {

    private val _qrCodeImage = MutableLiveData<Bitmap?>()
    val qrCodeImage: MutableLiveData<Bitmap?>
        get() = _qrCodeImage

    fun generateQr(playStoreUrl: String) {
        val qrCodeBitmap = settingUseCase.generateQr(playStoreUrl)
        _qrCodeImage.value = qrCodeBitmap
    }

}