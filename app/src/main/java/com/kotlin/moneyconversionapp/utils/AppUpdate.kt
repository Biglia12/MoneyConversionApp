package com.kotlin.moneyconversionapp.utils

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed

class AppUpdate (activity: Activity) : InstallStateUpdatedListener, InterfaceAppUpdate {

    private var appUpdateManager: AppUpdateManager
    private val MY_REQUEST_CODE: Int = 100
    private var parentActivity: Activity = activity
    private val updateType = AppUpdateType.IMMEDIATE

    var view: InterfaceAppUpdate.view? = null

    private val installStateUpdateListener: InstallStateUpdatedListener?

    init {

        appUpdateManager = AppUpdateManagerFactory.create(parentActivity)

        installStateUpdateListener = if (updateType == AppUpdateType.FLEXIBLE) {
            InstallStateUpdatedListener { state ->
                if (state.installStatus() == InstallStatus.DOWNLOADED) {
                    setDownloadToast("Descarga exitosa. Reiniciando app en breve.")
                    appUpdateManager.completeUpdate()
                }
            }
        } else {
            null
        }

        if (updateType == AppUpdateType.FLEXIBLE) {
            installStateUpdateListener?.let {
                appUpdateManager.registerListener(it)
            }
        }

        checkForAppUpdate()
    }

    fun checkForAppUpdate() {
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
                    parentActivity,
                    MY_REQUEST_CODE
                )
            }
        }
    }

     fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != AppCompatActivity.RESULT_OK) {
            println("Something Error")
        }
    }

    fun onResume() {
        if (updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        updateType,
                        parentActivity,
                        MY_REQUEST_CODE
                    )
                }
            }
        }
    }

    fun onDestroy() {
        if (updateType == AppUpdateType.IMMEDIATE) {
            installStateUpdateListener?.let {
                appUpdateManager.unregisterListener(it)
            }
        }
    }

    override fun setDownloadToast(download: String) {
        view!!.getDownloadToast(download)
    }

    override fun onStateUpdate(state: InstallState) {
        TODO("Not yet implemented")
    }
}