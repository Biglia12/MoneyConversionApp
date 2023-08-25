package com.kotlin.moneyconversionapp.ui.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.play.core.appupdate.AppUpdateManager
import com.kotlin.moneyconversionapp.MoneyApplication
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ActivityMainBinding
import com.kotlin.moneyconversionapp.ui.view.fragments.CalculatorModule.CalculatorFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardModule.DashBoardFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule.HistoryFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.SettingsModule.SettingsFragment
import com.kotlin.moneyconversionapp.ui.viewmodel.Main.MainViewModel
import com.kotlin.moneyconversionapp.ui.viewmodel.Main.MainViewModelFactory
import com.kotlin.moneyconversionapp.utils.AppUpdate
import com.kotlin.moneyconversionapp.utils.InterfaceAppUpdate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), InterfaceAppUpdate.view {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var dashBoardFragment: DashBoardFragment

    @Inject
    lateinit var historyFragment: HistoryFragment

    @Inject
    lateinit var calculatorFragment: CalculatorFragment

    @Inject
    lateinit var settingsFragment: SettingsFragment

    private lateinit var currentFragment: Fragment

    private val moneyApplication: MoneyApplication = MoneyApplication()

    /*   private val mainViewModel: MainViewModel by viewModels {
           MainViewModelFactory(this)
       }*/

    private lateinit var appUpdate: AppUpdate

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("saveInstanceMain", savedInstanceState.toString())


        setStatusBar()

        appUpdate = AppUpdate(this)

        checkConecction()

    }

    private fun setStatusBar() {
        val window = this.window
        window.statusBarColor = resources.getColor(R.color.colorPrimary)
    }

    override fun onResume() {
        super.onResume()
        appUpdate.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        appUpdate.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        appUpdate.onDestroy()
        Log.i("onDestroyMainActivity", "main destroyed")
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
                R.id.item_settings -> showFragment(settingsFragment)
            }
            true
        }
    }

    private fun showFragment(fragment: Fragment) { // para que el fragmento no se vuevla a recrear. (lo hgacemos para no volver a llamar varias veces a el servicio sin nesecidad)
        val transaction = supportFragmentManager.beginTransaction()

        if (fragment.isAdded) {
            transaction.show(fragment)
        } else {
            transaction.add(R.id.fragment_container, fragment)
        }

        for (existingFragment in supportFragmentManager.fragments) {
            if (existingFragment != fragment) {
                transaction.hide(existingFragment)
            }
        }

        transaction.commit()
        currentFragment = fragment
    }

    override fun getDownloadToast(download: String) {
        Toast.makeText(this, download, Toast.LENGTH_SHORT).show()
    }


}