package com.hayde117.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    dialogOpened: Boolean,
    onDialogClosed: () -> Unit,
    onYesClicked: () -> Unit,
){
    if (dialogOpened){
        AlertDialog(
            title = {
                Text(text = title, fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold)
            },
            text = {
                Text(text = message, fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal)
            },
            confirmButton = {
                Button(onClick = {
                    onYesClicked()
                    onDialogClosed()
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDialogClosed) {
                    Text(text = "NO")
                }
            },
            onDismissRequest = onDialogClosed
            
        
        ) 
    }
}