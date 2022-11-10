package com.assignment.rnt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.rnt.ui.screen.home.HomeScreen

/**
 * NavHost composable.
 */
@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") {
            HomeScreen(
                onNavigateToNumberTest = {
                    // todo navigate to NumberTest screen.
                })
        }
        //todo NumberTest screen.
    }
}