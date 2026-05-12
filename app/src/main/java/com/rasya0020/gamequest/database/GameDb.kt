package com.rasya0020.gamequest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rasya0020.gamequest.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
abstract class GameDb : RoomDatabase(){
    abstract fun gameDao(): GameDao

    companion object{
        @Volatile
        private var INSTANCE: GameDb? = null

        fun getDatabase(context: Context): GameDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDb::class.java,
                    "game_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}