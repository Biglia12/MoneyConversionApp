package com.kotlin.moneyconversionapp.ui.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isFlexibleUpdateAllowed
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import com.kotlin.moneyconversionapp.MoneyApplication
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ActivityMainBinding
import com.kotlin.moneyconversionapp.ui.view.fragments.CalculatorModule.CalculatorFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardModule.DashBoardFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule.HistoryFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.AccessController.getContext
import kotlin.time.Duration.Companion.seconds
import kotlin.time.seconds


//test
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dashBoardFragment = DashBoardFragment()
    private val historyFragment = HistoryFragment()
    private val calculatorFragment = CalculatorFragment()
    private lateinit var currentFragment: Fragment
    private val moneyApplication: MoneyApplication = MoneyApplication()

    /*private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(this)
    }*/
    private val MY_REQUEST_CODE: Int = 100

    private lateinit var appUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.FLEXIBLE

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar // ocultamos el action bar
        actionBar!!.hide()

        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)

        //mainViewModel.checkAppUpdate()

        checkConecction()

        if (updateType == AppUpdateType.FLEXIBLE){
            appUpdateManager.registerListener(installStateUpdateListener)
        }

        checkForAppUpdate()
        // observeLiveData()

    }

    private val installStateUpdateListener = InstallStateUpdatedListener { state ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            Toast.makeText(
                this@MainActivity,
                "Descarga exitosa. Reiniciando app en 5 segundos.",
                Toast.LENGTH_SHORT
            )
                .show()
            lifecycleScope.launch {
                delay(5000)
                appUpdateManager.completeUpdate()
            }
        }
    }

    private fun checkForAppUpdate() {
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
                    this,
                    MY_REQUEST_CODE
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    appUpdateManager.startUpdateFlowForResult(
                        info,
                        updateType,
                        this,
                        MY_REQUEST_CODE
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            println("Something Error")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (updateType == AppUpdateType.FLEXIBLE){
            appUpdateManager.unregisterListener(installStateUpdateListener)
        }
    }

    /*
        private fun checkForAppUpdate() {

            val appUpdateInfoTask = appUpdateManager.appUpdateInfo

    // Checks that the platform will allow the specified type of update.
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ) {
                    // Request the update.
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, MY_REQUEST_CODE)
                }
            }

            // Before starting an update, register a listener for updates.
            appUpdateManager.registerListener(listener)
        }


        val listener = { state: InstallState ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                 popupSnackbarForCompleteUpdate()
            }
        }

        private fun popupSnackbarForCompleteUpdate() {
            val snackbar = Snackbar.make(
                findViewById(R.id.main_activity),
                "An update has just been downloaded.",
                Snackbar.LENGTH_INDEFINITE
            )
            snackbar.setAction("INSTALL") { view: View? -> appUpdateManager.completeUpdate() }
            snackbar.setActionTextColor(
                resources.getColor(com.kotlin.moneyconversionapp.R.color.colorPrimary)
            )
            snackbar.show()
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == MY_REQUEST_CODE) {
                if (resultCode != RESULT_OK) {
                    Log.e("MY_APP", "Update flow failed! Result code: $resultCode")
                }
            }
        }

        override fun onStop() {
            super.onStop()
            appUpdateManager.unregisterListener(listener)
        }

        override fun onResume() {
            super.onResume()
            appUpdateManager
                .appUpdateInfo
                .addOnSuccessListener { appUpdateInfo ->
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate()
                    }
                }
        }
    */

    /* private fun observeLiveData() {
             mainViewModel.showSnackbarEvent.observe(this) {
                 showSnackbar()
             }
         }

         private fun showSnackbar() {
             Snackbar.make(
                 findViewById(R.id.main_activity),
                 "An update has just been downloaded.",
                 Snackbar.LENGTH_INDEFINITE
             ).apply {
                 setAction("INSTALAR") { mainViewModel.appUpdateManager.completeUpdate() }
                 setActionTextColor(resources.getColor(R.color.colorPrimary))
                 show()
             }
         }

         override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
             super.onActivityResult(requestCode, resultCode, data)
             mainViewModel.onActivityResult(requestCode, resultCode, data)
         }

         override fun onStop() {
             super.onStop()
             mainViewModel.onStop()
         }

         override fun onResume() {
             super.onResume()
             mainViewModel.onResume()
         }*/

    private fun checkConecction() {
        if (moneyApplication.isConnected(this)) {
            binding.constraintErrorInternet.visibility = GONE
            binding.navigationBottom.visibility = VISIBLE
            binding.fragmentContainer.visibility = VISIBLE
            bottomNavigation()
        } else {
            binding.constraintErrorInternet.visibility = VISIBLE
            binding.navigationBottom.visibility = GONE
            binding.retryErrorButton.setOnClickListener {
                checkConecction()
            }
        }
    }


    private fun bottomNavigation() {

        currentFragment = dashBoardFragment
        showFragment(currentFragment)//init first fragment when app run the first time

        binding.navigationBottom.setOnItemSelectedListener {
            when (it.itemId) {
                com.kotlin.moneyconversionapp.R.id.item_resume -> showFragment(dashBoardFragment)
                com.kotlin.moneyconversionapp.R.id.item_history -> showFragment(historyFragment)
                com.kotlin.moneyconversionapp.R.id.item_conversor -> showFragment(calculatorFragment)
            }
            true
        }
    }

    private fun showFragment(fragment: Fragment) { // para que el fragmento no se vuevla a recrear. (lo hgacemos para no volver a llamar varias veces a el servicio sin nesecidad)
        supportFragmentManager.beginTransaction().apply {
            hide(currentFragment)
            if (fragment.isAdded) {
                show(fragment)
            } else {
                add(com.kotlin.moneyconversionapp.R.id.fragment_container, fragment)
                show(fragment)
            }
            commit()
        }
        currentFragment = fragment
    }

    /*private fun replaceFragment(fragment: Fragment) {// para volver a recrear el fragmento

        supportFragmentManager.beginTransaction().apply {

            replace(R.id.fragment_container, fragment)
            commit()

        }
    }*/
}