package com.kotlin.moneyconversionapp.ui.viewmodel.Setting

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.audiofx.Equalizer.Settings
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.data.model.settings.SettingsModel
import com.kotlin.moneyconversionapp.domain.usecases.DollarUseCases
import com.kotlin.moneyconversionapp.domain.usecases.SettingUseCase
import javax.inject.Inject

class SettingsViewModel  @Inject constructor(private val settingUseCase: SettingUseCase): ViewModel() {

}