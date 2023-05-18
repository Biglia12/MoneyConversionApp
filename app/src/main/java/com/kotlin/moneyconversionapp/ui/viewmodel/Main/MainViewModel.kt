package com.kotlin.moneyconversionapp.ui.viewmodel.Main

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

class MainViewModel(val activity: Activity) : ViewModel() {

    val appUpdateManager = AppUpdateManagerFactory.create(activity)
    private val MY_REQUEST_CODE: Int = 100

    // Returns an intent object that you use to check for an update.
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo

    private val _showSnackbarEvent = MutableLiveData<Unit>()
    val showSnackbarEvent: LiveData<Unit>
        get() = _showSnackbarEvent

    init {

        checkAppUpdate()

    }

    private val listener = { state: InstallState ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            // After the update is downloaded, show a notification
            // and request user confirmation to restart the app.
            _showSnackbarEvent.value = Unit
        }
    }

    fun checkAppUpdate() {
        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                // Request the update.
                appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    activity,
                    // Include a request code to later monitor this update request.
                    MY_REQUEST_CODE
                )
            }
        }

        // Before starting an update, register a listener for updates.
        appUpdateManager.registerListener(listener)

    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    fun onStop (){
       appUpdateManager.unregisterListener(listener)
    }

    fun onResume(){
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                // If the update is downloaded but not installed,
                // notify the user to complete the update.
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    _showSnackbarEvent.value = Unit
                }
            }
    }

}