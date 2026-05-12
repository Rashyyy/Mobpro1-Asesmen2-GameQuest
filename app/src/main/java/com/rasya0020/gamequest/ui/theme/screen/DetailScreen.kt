package com.rasya0020.gamequest.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rasya0020.gamequest.database.GameDb
import com.rasya0020.gamequest.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    gameId: Long = -1L,
    onSaved: () -> Unit,
    onBack: () -> Unit,
) {
    val context = LocalContext.current

    val viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            GameDb.getInstance(context).gameDao(),
            context
            )
    )

    var judul by remember { mutableStateOf("") }
    var gaya by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }

    LaunchedEffect(gameId) {
        if (gameId != -1L) {
            val game = viewModel.getGame(gameId)
            game?.let {
                judul = it.judul
                gaya = it.gayaMain
                target = it.targetJam.toString()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (gameId == -1L) "Tambah Game" else "Edit Game") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (gameId == -1L) {
                    viewModel.insert(judul, gaya, target.toIntOrNull() ?: 0)
                } else {
                    viewModel.update(gameId, judul, gaya, target.toIntOrNull() ?: 0)
                }
                onSaved()
            }) {
                Icon(Icons.Default.Save, contentDescription = "Save Game")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = judul,
                onValueChange = { judul = it },
                label = { Text("Judul Game") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = gaya,
                onValueChange = { gaya = it },
                label = { Text("Gaya Main") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = target,
                onValueChange = { target = it },
                label = { Text("Target Jam") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }
    }
}