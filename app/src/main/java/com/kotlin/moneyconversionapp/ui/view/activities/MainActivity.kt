package com.kotlin.moneyconversionapp.ui.view.activities

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kotlin.moneyconversionapp.MoneyApplication
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ActivityMainBinding
import com.kotlin.moneyconversionapp.ui.view.fragments.CalculatorModule.CalculatorFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardModule.DashBoardFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule.HistoryFragment

//test
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dashBoardFragment = DashBoardFragment()
    private val historyFragment = HistoryFragment()
    private val calculatorFragment = CalculatorFragment()
    private lateinit var currentFragment: Fragment
    private val moneyApplication: MoneyApplication = MoneyApplication(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar // ocultamos el action bar
        actionBar!!.hide()

        checkConecction()

    }

    private fun checkConecction() {
        if (moneyApplication.isConnected()) {
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

    private fun showFragment(fragment: Fragment) { // para que el fragmento no se vuevla a recrear
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