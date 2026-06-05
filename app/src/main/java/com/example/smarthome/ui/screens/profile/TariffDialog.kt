package com.example.smarthome.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smarthome.data.model.GlobalTariff

@Composable
fun TariffDialog(
    globalTariffs: List<GlobalTariff>,
    currentProvider: String,
    currentPlanType: String,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var selectedProvider by remember { mutableStateOf(currentProvider) }
    var selectedPlanType by remember { mutableStateOf(currentPlanType) }

    val providers = globalTariffs.map { it.provider }.distinct()
    val planTypes = listOf("SINGLE_ZONE", "TWO_ZONE")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Tariff") },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Provider", fontWeight = FontWeight.Bold)
                providers.forEach { provider ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = selectedProvider == provider,
                            onClick = { selectedProvider = provider }
                        )
                        Text(text = provider)
                    }
                }
                Text("Plan Type", fontWeight = FontWeight.Bold)
                planTypes.forEach { planType ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = selectedPlanType == planType,
                            onClick = { selectedPlanType = planType }
                        )
                        Text(text = planType)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(selectedProvider, selectedPlanType) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}