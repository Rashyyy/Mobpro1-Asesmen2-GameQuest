package com.rasya0020.gamequest.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecycleBinScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val deletedList by viewModel.deletedGames.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recycle Bin") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        if (deletedList.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Tidak ada data di keranjang sampah")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(deletedList) { item ->
                    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = item.game.judul, style = MaterialTheme.typography.titleMedium)
                                Text(text = "Kategori: ${item.namaKategori}", style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(onClick = { viewModel.restoreGame(item.game.id) }) {
                                Icon(Icons.Default.Restore, contentDescription = "Restore", tint = Color.Green)
                            }
                            IconButton(onClick = { viewModel.deletePermanently(item.game.id) }) {
                                Icon(Icons.Default.DeleteForever, contentDescription = "Hapus Total", tint = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}