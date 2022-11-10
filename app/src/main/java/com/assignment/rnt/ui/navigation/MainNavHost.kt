package com.assignment.rnt.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assignment.rnt.ui.screen.home.HomeScreen
import com.assignment.rnt.ui.screen.number.NumberTestScreen
import com.assignment.rnt.ui.viewmodel.NumberTestViewModel

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
                    navController.navigate("numberTest")
                })
        }
        composable("numberTest") {
            val viewModel = hiltViewModel<NumberTestViewModel>()
            NumberTestScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.navigateUp() })
        }
    }
}