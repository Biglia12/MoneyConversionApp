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
import com.kotlin.moneyconversionapp.ui.view.fragments.CalculatorModule.CalculatorFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardModule.DashBoardFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule.HistoryFragment
import com.kotlin.moneyconversionapp.ui.viewmodel.Main.MainViewModel
import com.kotlin.moneyconversionapp.ui.viewmodel.Main.MainViewModelFactory


//test
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dashBoardFragment = DashBoardFragment()
    private val historyFragment = HistoryFragment()
    private val calculatorFragment = CalculatorFragment()
    private lateinit var currentFragment: Fragment
    private val moneyApplication: MoneyApplication = MoneyApplication()
    private lateinit var navController: NavController

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(this)
    }

    private lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val actionBar: ActionBar? = supportActionBar // ocultamos el action bar
        //actionBar!!.hide()



        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)

        checkConecction()

        observableLive()

    }

    private fun observableLive() {

        mainViewModel.setAppUpdateManager(appUpdateManager)
        mainViewModel.updateType()
        mainViewModel.checkForAppUpdate(this)

        mainViewModel.downloadSuccessLiveData.observe(this, Observer { isSuccess ->
            if (isSuccess) {
                Toast.makeText(
                    applicationContext,
                    "Descarga exitosa. Reiniciando app en breve.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


    }

    override fun onResume() {
        super.onResume()
        mainViewModel.onResume(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            println("Something Error")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.onDestroy()
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

   /* private fun bottomNavigation() {

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


        private fun replaceFragment(fragment: Fragment) {// para volver a recrear el fragmento

        supportFragmentManager.beginTransaction().apply {

            replace(R.id.fragment_container, fragment)
            commit()

        }
    }*/
}

