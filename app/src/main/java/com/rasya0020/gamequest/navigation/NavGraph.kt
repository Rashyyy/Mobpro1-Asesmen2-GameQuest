package com.rasya0020.gamequest.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rasya0020.gamequest.ui.screen.MainScreen
import com.rasya0020.gamequest.ui.screen.MainViewModel

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object AddEdit : Screen("add_edit?gameId={gameId}") {
        fun createRoute(gameId: Long = -1L) = "add_edit?gameId=$gameId"
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(
                viewModel = mainViewModel,
                onAddClick = { navController.navigate(Screen.AddEdit.createRoute()) },
                onItemClick = { id -> navController.navigate(Screen.AddEdit.createRoute(id)) }
            )
        }
        composable(
            route = Screen.AddEdit.route,
            arguments = listOf(navArgument("gameId") { type = NavType.LongType; defaultValue = -1L })
        ) {

        }
    }
}