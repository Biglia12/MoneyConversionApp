package com.kotlin.moneyconversionapp.ui.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.databinding.ActivityMainBinding
import com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.HistoryFragment
import com.kotlin.moneyconversionapp.ui.viewmodel.DollarViewModel

//test
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dashBoardFragment = DashBoardFragment()
    private val historyFragment = HistoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigation()

    }

    private fun bottomNavigation() {

        replaceFragment(dashBoardFragment)//init first fragment when app run the first time

        binding.navigationBottom.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_resume -> replaceFragment(dashBoardFragment)
                R.id.item_history -> replaceFragment(historyFragment)
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