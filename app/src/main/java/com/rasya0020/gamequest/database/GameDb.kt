package com.rasya0020.gamequest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rasya0020.gamequest.model.Category
import com.rasya0020.gamequest.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Game::class, Category::class], version = 2, exportSchema = false)
abstract class GameDb : RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameDb? = null
        fun getInstance(context: Context): GameDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameDb::class.java,
                        "game_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                INSTANCE?.let { database ->
                                    CoroutineScope(Dispatchers.IO).launch {
                                        val dao = database.gameDao()
                                        dao.insertCategory(Category(namaKategori = "RPG"))
                                        dao.insertCategory(Category(namaKategori = "FPS"))
                                        dao.insertCategory(Category(namaKategori = "Action"))
                                        dao.insertCategory(Category(namaKategori = "Simulation"))
                                    }
                                }
                            }
                        })
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}