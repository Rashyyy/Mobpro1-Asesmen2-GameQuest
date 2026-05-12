package com.rasya0020.gamequest.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasya0020.gamequest.database.GameDao
import com.rasya0020.gamequest.model.Category
import com.rasya0020.gamequest.model.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: GameDao) : ViewModel() {

    fun insert(judul: String, gaya: String, target: Int, categoryId: Long) {
        viewModelScope.launch {
            val game = Game(
                judul = judul,
                gayaMain = gaya,
                targetJam = target,
                categoryId = categoryId,
                isDeleted = false
            )
            dao.insertGame(game)
        }
    }

    suspend fun getGame(id: Long): Game? {
        return dao.getGameById(id)
    }

    fun update(id: Long, judul: String, gaya: String, target: Int, categoryId: Long) {
        viewModelScope.launch {
            val game = Game(
                id = id,
                judul = judul,
                gayaMain = gaya,
                targetJam = target,
                categoryId = categoryId,
                isDeleted = false
            )
            dao.updateGame(game)
        }
    }

    val allCategories: StateFlow<List<Category>> = dao.getAllCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteGame(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.softDeleteById(id)
        }
    }
}