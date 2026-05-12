package com.rasya0020.gamequest.ui.theme.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import com.rasya0020.gamequest.model.Game

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
            if (gameIdToDelete != -1L) viewModel.deleteGame(gameIdToDelete)
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
                items(listGame) { game ->
                    GameItem(
                        game = game,
                        onClick = { onItemClick(game.id) },
                        onDelete = {
                            gameIdToDelete = game.id
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
                items(listGame) { game ->
                    GameItem(
                        game = game,
                        onClick = { onItemClick(game.id) },
                        onDelete = {
                            gameIdToDelete = game.id
                            showDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GameItem(game: Game, onClick: () -> Unit, onDelete: () -> Unit) {
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
                    text = game.judul,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Gaya: ${game.gayaMain}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Target: ${game.targetJam} Jam",
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