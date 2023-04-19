package com.kotlin.moneyconversionapp.ui.view.activities.ui.theme

import com.kotlin.moneyconversionapp.R

sealed class ItemMenu(
    val icon: Int,
    val tittle: String,
    val route: String
){
    object DashBoard: ItemMenu(R.drawable.ic_baseline_dashboard, "DashBoard", "DashBoard")
    object Calculator: ItemMenu(R.drawable.ic_baseline_calculate_24, "Calculator", "Calculator")
    object Historic: ItemMenu(R.drawable.ic_baseline_show_chart_24, "Graphic", "Historic")
}
