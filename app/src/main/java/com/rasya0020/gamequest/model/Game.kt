package com.rasya0020.gamequest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class Game (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val judul: String,
    val gayaMain: String,
    val waktuMain: Int = 0,
    val targetJam: Int
)