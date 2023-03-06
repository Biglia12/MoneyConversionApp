package com.kotlin.moneyconversionapp.ui.view.activities

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kotlin.moneyconversionapp.MoneyApplication
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ActivityMainBinding
import com.kotlin.moneyconversionapp.ui.view.fragments.CalculatorFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.HistoryFragment

//test
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dashBoardFragment = DashBoardFragment()
    private val historyFragment = HistoryFragment()
    private val calculatorFragment = CalculatorFragment()
    private val moneyApplication: MoneyApplication = MoneyApplication(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        replaceFragment(dashBoardFragment)//init first fragment when app run the first time

        binding.navigationBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_resume -> {
                    replaceFragment(dashBoardFragment)

                }
                R.id.item_history -> replaceFragment(historyFragment)
                R.id.item_conversor -> replaceFragment(calculatorFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().apply {

            replace(R.id.fragment_container, fragment)
            commit()

        }
    }
}