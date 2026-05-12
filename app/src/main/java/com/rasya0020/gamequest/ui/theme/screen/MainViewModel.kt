package com.rasya0020.gamequest.ui.theme.screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rasya0020.gamequest.database.GameDb
import com.rasya0020.gamequest.model.Game
import com.rasya0020.gamequest.util.SettingsDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = GameDb.getDatabase(application).gameDao()
    private val dataStore = SettingsDataStore(application)

    val data: StateFlow<List<Game>> = dao.getAllGames()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val layoutMode: StateFlow<Boolean> = dataStore.layoutFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun toggleLayout() {
        viewModelScope.launch {
            dataStore.saveLayout(!layoutMode.value)
        }
    }

    fun deleteGame(game: Game) {
        viewModelScope.launch {
            dao.deleteGame(game)
        }
    }
}