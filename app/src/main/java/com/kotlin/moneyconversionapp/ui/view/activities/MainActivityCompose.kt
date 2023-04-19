package com.kotlin.moneyconversionapp.ui.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.kotlin.moneyconversionapp.ui.view.activities.ui.theme.ItemMenu
import com.kotlin.moneyconversionapp.ui.view.activities.ui.theme.MoneyConversionAppTheme
import com.kotlin.moneyconversionapp.ui.view.fragments.DashBoardModule.DashBoardFragment

class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyConversionAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DashBoard()
                }
            }
        }
    }
}

@Composable
fun DashBoard() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val navigtionItem = listOf(
        ItemMenu.DashBoard,
        ItemMenu.Calculator,
        ItemMenu.Historic
    )
}

@Preview(showBackground = true)
@Composable
fun DashBoardPreview() {
    MoneyConversionAppTheme {
        DashBoard()
    }
}