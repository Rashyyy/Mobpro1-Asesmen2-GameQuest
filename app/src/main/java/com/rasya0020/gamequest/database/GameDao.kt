package com.rasya0020.gamequest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rasya0020.gamequest.model.Category
import com.rasya0020.gamequest.model.Game
import com.rasya0020.gamequest.model.GameWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game_table WHERE isDeleted = 0 ORDER BY judul ASC")
    fun getAllGames(): Flow<List<Game>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Update
    suspend fun updateGame(game: Game)

    @Query("DELETE FROM game_table WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM game_table WHERE id = :id")
    suspend fun getGameById(id: Long): Game?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Query("SELECT * FROM categories_table ORDER BY namaKategori ASC")
    fun getAllCategories(): Flow<List<Category>>

    @Query("""
        SELECT game_table.*, categories_table.namaKategori 
        FROM game_table 
        INNER JOIN categories_table ON game_table.categoryId = categories_table.categoryId
        WHERE game_table.isDeleted = 0
    """)
    fun getGamesWithCategory(): Flow<List<GameWithCategory>>
}