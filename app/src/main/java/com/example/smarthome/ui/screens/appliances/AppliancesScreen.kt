package com.example.smarthome.ui.screens.appliances

import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.smarthome.ui.theme.CardBackground


@Composable
fun AppliancesScreen(viewModel: AppliancesViewModel = viewModel() ) {
    val uiState by viewModel.uiState.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddDeviceDialog(
            onDismiss = { showDialog = false },
            onConfirm = { name, watts, hours ->
                viewModel.addAppliance(name, watts, hours)
                showDialog = false
            }
        )
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showDialog = true},
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add device",
                    tint= MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 24.dp, end = 24.dp, top = 24.dp)
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Text(
                text = "Devices",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(24.dp))

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                uiState.error != null -> {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                uiState.appliances.isEmpty() -> {
                    Text(
                        text = "No devices yet. Add your first device.",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }

                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(uiState.appliances) { appliance ->
                            Card (
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = CardBackground
                                )
                            ){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = appliance.name,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            fontSize = 16.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "${appliance.wattTypical}W · ${appliance.hoursPerDay}h/day",
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.6f
                                            ),
                                            fontSize = 12.sp
                                        )
                                        if (appliance.tip != null) {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "💡 ${appliance.tip}",
                                                color = MaterialTheme.colorScheme.primary,
                                                fontSize = 12.sp
                                            )
                                        }
                                    }

                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "${"%.2f".format(appliance.monthlyCostLei)} lei",
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            text = "${"%.1f".format(appliance.percentOfBill)}% of bill",
                                            color = MaterialTheme.colorScheme.onBackground.copy(
                                                alpha = 0.6f
                                            ),
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun AddDeviceDialog(
    onDismiss: ()->Unit,
    onConfirm: (name: String,wattTypical: Int,hoursPerDay: Double) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var watts by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest=onDismiss,
        title={Text("Add Device")},
        containerColor = CardBackground,
        text={
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {name = it },
                    label = {Text("Device name")},
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = watts,
                    onValueChange = {watts = it },
                    label = {Text("Power (Watts)")},
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = hours,
                    onValueChange = {hours = it },
                    label = {Text("Hours per day")},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton={
            Button(onClick = {
                val w = watts.toIntOrNull() ?: 0
                val h = hours.toDoubleOrNull() ?: 0.0
                if (name.isNotEmpty() && w > 0 && h > 0) {
                    onConfirm(name, w, h)
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}