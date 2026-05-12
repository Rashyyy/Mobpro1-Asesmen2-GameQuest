package com.rasya0020.gamequest.ui.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rasya0020.gamequest.model.GameWithCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onAddClick: () -> Unit,
    onItemClick: (Long) -> Unit
) {
    val listGame by viewModel.games.collectAsState(initial = emptyList())
    val isListMode by viewModel.layoutMode.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var gameIdToDelete by remember { mutableLongStateOf(-1L) }

    DisplayAlertDialog(
        openDialog = showDialog,
        onClose = { showDialog = false },
        onConfirm = {
            if (gameIdToDelete != -1L) {
                viewModel.deleteGame(gameIdToDelete)
                showDialog = false
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GameQuest 🎮", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { viewModel.toggleLayout() }) {
                        Icon(
                            imageVector = if (isListMode) Icons.Default.GridView else Icons.AutoMirrored.Filled.ViewList,
                            contentDescription = "Switch Layout"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Game")
            }
        }
    ) { padding ->
        if (listGame.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada game. Ayo tambah!", color = Color.Gray)
            }
        } else if (isListMode){
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listGame) { item ->
                    GameItem(
                        item = item,
                        onClick = { onItemClick(item.game.id) },
                        onDelete = {
                            gameIdToDelete = item.game.id
                            showDialog = true
                        }
                    )
                }
            }
        }
        else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                items(listGame) { item ->
                    GameItem(
                        item = item,
                        onClick = { onItemClick(item.game.id) },
                        onDelete = {
                            gameIdToDelete = item.game.id
                            showDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GameItem(item: GameWithCategory, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Gamepad,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = item.game.judul,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = item.namaKategori,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Text(
                    text = "Gaya: ${item.game.gayaMain}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Target: ${item.game.targetJam} Jam",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
            IconButton(onClick = onDelete){
                Icon(Icons.Default.Delete, contentDescription = "Delete Game", tint = Color.Red)
            }
        }
    }
}