package com.rasya0020.gamequest.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "game_table",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
            )
        ],
    indices = [Index(value = ["categoryId"])]
    )
data class Game (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val judul: String,
    val gayaMain: String,
    val waktuMain: Int = 0,
    val targetJam: Int,
    val categoryId: Long,
    val isDeleted: Boolean = false
)
