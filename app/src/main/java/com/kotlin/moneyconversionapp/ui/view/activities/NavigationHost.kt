package com.kotlin.moneyconversionapp.ui.view.activities

import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.composable
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kotlin.moneyconversionapp.ui.view.activities.ui.theme.ItemMenu
import com.kotlin.moneyconversionapp.ui.view.fragments.CalculatorModule.CalculatorFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardModule.DashBoardFragment
import com.kotlin.moneyconversionapp.ui.view.fragments.HistoricModule.HistoryFragment

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ItemMenu.DashBoard.route,
    ) {
        composable(ItemMenu.DashBoard.route) {
           DashBoardFragment()
        }

        composable(ItemMenu.Calculator.route){
          CalculatorFragment()
        }

        composable(ItemMenu.Historic.route){
            HistoryFragment()
        }
    }

}