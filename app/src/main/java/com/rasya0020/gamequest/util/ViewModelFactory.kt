package com.rasya0020.gamequest.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rasya0020.gamequest.database.GameDao
import com.rasya0020.gamequest.ui.theme.screen.DetailViewModel
import com.rasya0020.gamequest.ui.theme.screen.MainViewModel

class ViewModelFactory(private val dao: GameDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}