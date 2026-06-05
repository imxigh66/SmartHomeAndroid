package com.example.smarthome.ui.screens.billing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.model.BillingPeriod
import com.example.smarthome.data.model.CurrentMonthBilling
import com.example.smarthome.data.repository.BillingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BillingUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentMonth: CurrentMonthBilling? = null,
    val periods: List<BillingPeriod> = emptyList()
)

class BillingViewModel: ViewModel(){
    private val repository = BillingRepository()

    private val _uiState = MutableStateFlow(BillingUiState())
    val uiState: StateFlow<BillingUiState> =   _uiState

    init {
        loadBilling()
    }

    fun loadBilling() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val currentMonth = repository.getCurrentMonth()
                val periods = repository.getBillingPeriods()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        currentMonth = currentMonth,
                        periods = periods
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to load billing", isLoading = false) }
            }
        }
    }
}