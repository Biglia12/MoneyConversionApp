package com.kotlin.moneyconversionapp.ui.view.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.kotlin.moneyconversionapp.MoneyApplication
import com.kotlin.moneyconversionapp.databinding.ActivityMainBinding
import com.kotlin.moneyconversionapp.ui.viewmodel.Main.MainViewModel
import com.kotlin.moneyconversionapp.ui.viewmodel.Main.MainViewModelFactory
import com.kotlin.moneyconversionapp.utils.AppUpdate
import com.kotlin.moneyconversionapp.utils.InterfaceAppUpdate


//test
class MainActivity : AppCompatActivity(), InterfaceAppUpdate.view {

    private lateinit var binding: ActivityMainBinding
    private val moneyApplication: MoneyApplication = MoneyApplication()
    private lateinit var navController: NavController
    private lateinit var appUpdate: AppUpdate

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appUpdate = AppUpdate(this)


        checkConecction()


    }



    private fun checkConecction() {
        if (moneyApplication.isConnected(this)) {
            binding.constraintErrorInternet.visibility = GONE
            binding.navigationBottom.visibility = VISIBLE
            binding.fragmentContainerLinear.visibility = VISIBLE
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
        val navHosFragment =
            supportFragmentManager.findFragmentById(com.kotlin.moneyconversionapp.R.id.fragment_container) as NavHostFragment
        navController = navHosFragment.navController

        setupWithNavController(binding.navigationBottom, navController)
    }

    override fun onResume() {
        super.onResume()
        appUpdate.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        appUpdate.onActivityResult(requestCode,resultCode,data)
    }

    override fun onDestroy() {
        super.onDestroy()
        appUpdate.onDestroy()
    }

    override fun getDownloadToast(download: String) {
        Toast.makeText(this,download,Toast.LENGTH_SHORT).show()
    }



}

