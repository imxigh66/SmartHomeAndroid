package com.example.smarthome.ui.screens.appliances

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.model.Appliance
import com.example.smarthome.data.repository.AppliancesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class AppliancesUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val appliances: List<Appliance> = emptyList()
)

class AppliancesViewModel : ViewModel() {
    private val repository = AppliancesRepository()

    private val _uiState = MutableStateFlow(AppliancesUiState())
    val uiState: StateFlow<AppliancesUiState> = _uiState

    init {
        loadAppliances()
    }

    fun loadAppliances(){
        viewModelScope.launch {
             _uiState.update {it.copy(isLoading = true,error=null)}
            try {
                val list=repository.getAppliances()
                _uiState.update { it.copy(appliances = list, isLoading = false) }
            }catch (e: Exception){
                _uiState.update { it.copy(error="Failed to load devices", isLoading = false) }
            }
        }
    }

    fun addAppliance(name: String, wattTypical: Int, hoursPerDay: Double) {
        viewModelScope.launch {
            try {
                repository.addAppliance(
                    name = name,
                    icon = "device",
                    wattTypical = wattTypical,
                    hoursPerDay = hoursPerDay
                )
                loadAppliances()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to add device") }
            }
        }
    }
}