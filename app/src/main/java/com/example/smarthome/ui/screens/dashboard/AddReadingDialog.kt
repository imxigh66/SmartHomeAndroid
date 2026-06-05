package com.example.smarthome.ui.screens.dashboard


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarthome.ui.theme.CardBackground

@Composable
fun AddReadingDialog(
    onDismiss:()->Unit,
    onSuccess:()->Unit,
    viewModel: AddReadingViewModel= viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isSuccess) {
        if(uiState.isSuccess) onSuccess()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Meter Reading") },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        text={
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = uiState.dayReading,
                    onValueChange = {viewModel.onDayReadingChange(it)},
                    label = {Text("Day Reading (kWh)")},
                    modifier = Modifier.fillMaxWidth()
                )

                if(uiState.isTwoZone){
                    OutlinedTextField(
                        value = uiState.nightReading,
                        onValueChange = {viewModel.onNightReadingChange(it)},
                        label = {Text("Night Reading (kWh)")},
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Two-zone tariff")
                    Switch(
                        checked = uiState.isTwoZone,
                        onCheckedChange = {viewModel.onTwoZoneChange(it)}
                    )
                }

                uiState.error?.let{
                    Text(
                        text=it,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }


        },
        confirmButton = {
            Button(
                onClick = { viewModel.addReading() },
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp))
                } else {
                    Text("Save")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}