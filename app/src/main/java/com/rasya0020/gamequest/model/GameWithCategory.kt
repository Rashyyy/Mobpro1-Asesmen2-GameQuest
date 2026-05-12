package com.rasya0020.gamequest.model

import androidx.room.Embedded

data class GameWithCategory(
    @Embedded val game: Game,
    val namaKategori: String
)