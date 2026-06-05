package com.example.smarthome.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.repository.ReadingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddReadingUiState(
    val dayReading: String = "",
    val nightReading: String = "",
    val isTwoZone: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class AddReadingViewModel : ViewModel(){
    private val repository = ReadingsRepository()

    private val _uiState= MutableStateFlow(AddReadingUiState())
    val uiState: StateFlow<AddReadingUiState> = _uiState

    fun onDayReadingChange(value: String) {
        _uiState.update { it.copy(dayReading = value) }
    }

    fun onNightReadingChange(value: String) {
        _uiState.update { it.copy(nightReading = value) }
    }

    fun onTwoZoneChange(value: Boolean) {
        _uiState.update { it.copy(isTwoZone = value) }
    }

    fun addReading(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.addReading(
                    dayReading = _uiState.value.dayReading.toDoubleOrNull() ?: 0.0,
                    nightReading = _uiState.value.nightReading.toDoubleOrNull() ?: 0.0,
                    isTwoZone = _uiState.value.isTwoZone
                )
                _uiState.update { it.copy(isSuccess = true) }
            }catch (e: Exception){
                _uiState.update { it.copy(error = "Failed to add reading") }
            }finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}