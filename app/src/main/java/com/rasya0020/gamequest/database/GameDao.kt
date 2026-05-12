package com.rasya0020.gamequest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rasya0020.gamequest.model.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game_table ORDER BY judul ASC")
    fun getAllGames(): Flow<List<Game>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Update
    suspend fun updateGame(game: Game)

    @Query("DELETE FROM game_table WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM game_table WHERE id = :id")
    suspend fun getGameById(id: Long): Game?
}