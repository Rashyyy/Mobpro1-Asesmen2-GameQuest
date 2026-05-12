package com.rasya0020.gamequest.ui.theme.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasya0020.gamequest.database.GameDao
import com.rasya0020.gamequest.model.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val dao: GameDao) : ViewModel() {

    fun insert(judul: String, gaya: String, target: Int) {
        val game = Game(
            judul = judul,
            gayaMain = gaya,
            targetJam = target
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertGame(game)
        }
    }

    suspend fun getGame(id: Long): Game? {
        return dao.getGameById(id)
    }

    fun update(id: Long, judul: String, gaya: String, target: Int) {
        val game = Game(
            id = id,
            judul = judul,
            gayaMain = gaya,
            targetJam = target
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateGame(game)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}