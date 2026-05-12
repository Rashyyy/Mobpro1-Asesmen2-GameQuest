package com.rasya0020.gamequest.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasya0020.gamequest.database.GameDao
import com.rasya0020.gamequest.model.GameWithCategory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.rasya0020.gamequest.util.SettingsDataStore

class MainViewModel(private val dao: GameDao, private val dataStore: SettingsDataStore) : ViewModel() {

    val games: StateFlow<List<GameWithCategory>> = dao.getGamesWithCategory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val deletedGames: StateFlow<List<GameWithCategory>> = dao.getDeletedGamesWithCategory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val layoutMode: StateFlow<Boolean> = dataStore.layoutFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)
    fun toggleLayout() {
        viewModelScope.launch {
            dataStore.saveLayout(!layoutMode.value)
        }
    }

    fun deleteGame(id: Long) {
        viewModelScope.launch {
            dao.softDeleteById(id)
        }
    }

    fun restoreGame(id: Long) {
        viewModelScope.launch {
            dao.restoreById(id)
        }
    }

    fun deletePermanently(id: Long) {
        viewModelScope.launch {
            dao.permanentDeleteById(id)
        }
    }
}