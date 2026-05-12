package com.rasya0020.gamequest.ui.theme.screen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    onClose: () -> Unit,
    onConfirm: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onClose,
            title = {
                Text(text = "Hapus Game?")
            },
            text = {
                Text(text = "Apakah kamu yakin ingin menghapus data game ini? Tindakan ini tidak bisa dibatalkan.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        onClose()
                    }
                ) {
                    Text(text = "Hapus")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onClose
                ) {
                    Text(text = "Batal")
                }
            }
        )
    }
}

