package com.rasya0020.gamequest.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object AddEdit : Screen("add_edit?gameId={gameId}") {
        fun createRoute(gameId: Long = -1L) = "add_edit?gameId=$gameId"
    }
    object RecycleBin : Screen("recycle_bin")
}