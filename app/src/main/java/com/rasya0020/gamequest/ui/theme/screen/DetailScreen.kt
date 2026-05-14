package com.rasya0020.gamequest.ui.theme.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rasya0020.gamequest.database.GameDb
import com.rasya0020.gamequest.model.Category
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

    val showError = remember { mutableStateOf(false) }

    var judul by remember { mutableStateOf("") }
    var gaya by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }

    val categories by viewModel.allCategories.collectAsState(initial = emptyList())
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var expanded by remember { mutableStateOf(false) }
    
    val showDeleteDialog = remember { mutableStateOf(false) }

    LaunchedEffect(gameId) {
        if (gameId != -1L) {
            val game = viewModel.getGame(gameId)
            game?.let {
                judul = it.judul
                gaya = it.gayaMain
                target = it.targetJam.toString()
                selectedCategory = categories.find { cat -> cat.categoryId == it.categoryId }
            }
        }
    }

    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text("Hapus Game") },
            text = { Text("Pindahkan game ini ke Recycle Bin?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteGame(gameId)
                    showDeleteDialog.value = false
                    onSaved()
                }) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog.value = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (gameId == -1L) "Tambah Game" else "Edit Game") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (gameId != -1L){
                        IconButton(onClick = { showDeleteDialog.value = true }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Game")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val targetInt = target.toIntOrNull()
                if (judul.isBlank() || gaya.isBlank() || target.isBlank() || selectedCategory == null){
                    showError.value = true
                    Toast.makeText(context, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
                } else if (targetInt == null) {
                    Toast.makeText(context, "Target harus berupa angka", Toast.LENGTH_SHORT).show()
                } else {
                    showError.value = false
                    if (gameId == -1L) {
                        viewModel.insert(judul, gaya, targetInt, selectedCategory!!.categoryId)
                    } else {
                        viewModel.update(gameId, judul, gaya, targetInt, selectedCategory!!.categoryId)
                    }
                    onSaved()
                }
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
                onValueChange = {
                    judul = it
                    if (it.isNotBlank()) showError.value = false
                },
                label = { Text("Judul Game") },
                isError = showError.value && judul.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory?.namaKategori ?: "Pilih Kategori",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kategori") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                    isError = showError.value && selectedCategory == null
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.namaKategori) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                                showError.value = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = gaya,
                onValueChange = {
                    gaya = it
                    if (it.isNotBlank()) showError.value = false
                },
                label = { Text("Gaya Main") },
                isError = showError.value && gaya.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = target,
                onValueChange = {
                    target = it
                    if (it.isNotBlank()) showError.value = false
                },
                label = { Text("Target Jam") },
                isError = showError.value && target.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )
        }
    }
}
