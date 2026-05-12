package com.rasya0020.gamequest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long = 0,
    val namaKategori: String
)