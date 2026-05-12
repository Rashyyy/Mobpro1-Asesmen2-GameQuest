package com.rasya0020.gamequest.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rasya0020.gamequest.ui.theme.screen.MainScreen
import com.rasya0020.gamequest.ui.theme.screen.MainViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = androidx.compose.ui.platform.LocalContext.current

    val database = com.rasya0020.gamequest.database.GameDb.getInstance(context)
    val dao = database.gameDao()

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            val mainViewModel: MainViewModel = viewModel(
                factory = com.rasya0020.gamequest.util.ViewModelFactory(
                    com.rasya0020.gamequest.database.GameDb.getInstance(context).gameDao(),
                    context
                )
            )
            MainScreen(
                viewModel = mainViewModel,
                onAddClick = { navController.navigate(Screen.AddEdit.createRoute()) },
                onItemClick = { id -> navController.navigate(Screen.AddEdit.createRoute(id)) },
                onRecycleBinClick = { navController.navigate(Screen.RecycleBin.route) }
            )
        }
        composable(
            route = Screen.AddEdit.route,
            arguments = listOf(navArgument("gameId") { type = NavType.LongType; defaultValue = -1L })
        ) { backStackEntry ->
            val gameId = backStackEntry.arguments?.getLong("gameId") ?: -1L

            com.rasya0020.gamequest.ui.theme.screen.DetailScreen(
                gameId = gameId,
                onSaved = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.RecycleBin.route) {
            val mainViewModel: MainViewModel = viewModel(
                factory = com.rasya0020.gamequest.util.ViewModelFactory(dao, context)
            )
            com.rasya0020.gamequest.ui.theme.screen.RecycleBinScreen(
                viewModel = mainViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}