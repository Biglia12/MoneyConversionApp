package com.kotlin.moneyconversionapp.ui.viewmodel.Main

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import com.kotlin.moneyconversionapp.ui.view.activities.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(val activity: Activity) : ViewModel() {

    val checkForAppUpdateLiveData = MutableLiveData<AppUpdateInfo?>()
    private val updateType = AppUpdateType.FLEXIBLE
    private lateinit var appUpdateManager: AppUpdateManager
    var downloadToast  = MutableLiveData<String>()
    private val MY_REQUEST_CODE: Int = 100
    val downloadSuccessLiveData = MutableLiveData<Boolean>()

  init {

  }

    fun setAppUpdateManager(manager: AppUpdateManager) {
        appUpdateManager = manager
    }

    private val installStateUpdateListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            //downloadToast.value = "Descarga exitosa. Reiniciando app en 5 segundos."
            viewModelScope.launch {
                delay(2000)
                appUpdateManager.completeUpdate()
                downloadSuccessLiveData.postValue(true)
            }
        }
    }
    fun updateType(){
        if (updateType == AppUpdateType.FLEXIBLE){
            appUpdateManager.registerListener(installStateUpdateListener)
        }
    }
    fun checkForAppUpdate(mainActivity: MainActivity) {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = when (updateType) {
                AppUpdateType.FLEXIBLE -> info.isFlexibleUpdateAllowed
                AppUpdateType.IMMEDIATE -> info.isImmediateUpdateAllowed
                else -> false
            }
            if (isUpdateAvailable) {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    updateType,
                    mainActivity,
                    MY_REQUEST_CODE
                )
            }
        }
    }

    fun onResume(mainActivity: MainActivity) {
        if (updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        updateType,
                        mainActivity,
                        MY_REQUEST_CODE
                    )
                }
            }
        }
    }

     fun onDestroy() {
        if (updateType == AppUpdateType.FLEXIBLE){
            appUpdateManager.unregisterListener(installStateUpdateListener)
        }
    }

}
