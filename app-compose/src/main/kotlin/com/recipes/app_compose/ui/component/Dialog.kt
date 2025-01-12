package com.recipes.app_compose.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun Dialog(enabled: Boolean = false, title: String, text: String, onDismiss: () -> Unit) {
    var showDialog by remember { mutableStateOf(enabled) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                onDismiss()
            },
            title = { Text(text = title) },
            text = { Text(text = text) },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    onDismiss()
                }) {
                    Text("Back")
                }
            },
        )
    }
}