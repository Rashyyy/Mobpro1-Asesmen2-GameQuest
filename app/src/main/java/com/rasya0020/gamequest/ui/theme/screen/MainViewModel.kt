package com.rasya0020.gamequest.ui.theme.screen

import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasya0020.gamequest.database.GameDao
import com.rasya0020.gamequest.model.Game
import com.rasya0020.gamequest.model.GameWithCategory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.rasya0020.gamequest.util.SettingsDataStore
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val dao: GameDao, private val dataStore: SettingsDataStore) : ViewModel() {

    val games: StateFlow<List<GameWithCategory>> = dao.getGamesWithCategory()
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
            dao.deleteById(id)
        }
    }
}