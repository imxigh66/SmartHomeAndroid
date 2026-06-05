package com.example.smarthome.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.model.GlobalTariff
import com.example.smarthome.data.model.MyTariff
import com.example.smarthome.data.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val myTariff: MyTariff? = null,
    val globalTariffs: List<GlobalTariff> = emptyList(),
    val isDarkTheme: Boolean = true,
    val isSuccess: Boolean = false
)

class ProfileViewModel : ViewModel(){
    private val repository = ProfileRepository()

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val myTariff = repository.getMyTariff()
                val globalTariffs = repository.getGlobalTariffs()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        myTariff = myTariff,
                        globalTariffs = globalTariffs
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to load profile", isLoading = false) }
            }
        }
    }

    fun toggleTheme() {
        _uiState.update { it.copy(isDarkTheme = !it.isDarkTheme) }
    }

    fun updateTariff(provider: String, planType: String) {
        viewModelScope.launch {
            try {
                val global = _uiState.value.globalTariffs
                val singleRate = global.find { it.provider == provider && it.planType == planType && it.zone == "SINGLE" }?.rate ?: 0.0
                val dayRate = global.find { it.provider == provider && it.planType == planType && it.zone == "DAY" }?.rate ?: 0.0
                val nightRate = global.find { it.provider == provider && it.planType == planType && it.zone == "NIGHT" }?.rate ?: 0.0

                repository.updateTariff(provider, planType, singleRate, dayRate, nightRate)
                loadProfile()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update tariff") }
            }
        }
    }
}