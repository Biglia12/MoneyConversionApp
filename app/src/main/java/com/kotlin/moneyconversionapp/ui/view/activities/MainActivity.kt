package com.kotlin.moneyconversionapp.ui.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.kotlin.moneyconversionapp.MoneyApplication
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ActivityMainBinding
import com.kotlin.moneyconversionapp.ui.view.fragments.CalculatorModule.CalculatorFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardModule.DashBoardFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule.HistoryFragment
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModelFactory
import com.kotlin.moneyconversionapp.ui.viewmodel.Historic.HistoricDollarViewModel
import com.kotlin.moneyconversionapp.ui.viewmodel.Main.MainViewModel
import com.kotlin.moneyconversionapp.ui.viewmodel.Main.MainViewModelFactory
import com.onesignal.OneSignal

//test
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dashBoardFragment = DashBoardFragment()
    private val historyFragment = HistoryFragment()
    private val calculatorFragment = CalculatorFragment()
    private lateinit var currentFragment: Fragment
    private val moneyApplication: MoneyApplication = MoneyApplication()
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar // ocultamos el action bar
        actionBar!!.hide()

        mainViewModel.checkAppUpdate()

        checkConecction()
        observeLiveData()

    }

    private fun observeLiveData() {
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
            setAction("Intalacación") { mainViewModel.appUpdateManager.completeUpdate() }
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
    }

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
                R.id.item_resume -> showFragment(dashBoardFragment)
                R.id.item_history -> showFragment(historyFragment)
                R.id.item_conversor -> showFragment(calculatorFragment)
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
                add(R.id.fragment_container, fragment)
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