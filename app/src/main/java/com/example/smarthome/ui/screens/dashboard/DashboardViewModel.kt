package com.example.smarthome.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.api.RetrofitClient
import com.example.smarthome.data.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DashboardUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val forecastTotal: Double? = null,
    val monthAmount: Double? = null,
    val monthConsumption: Double? = null,
    val daysRemaining: Int = 0,
    val recommendation: String? = null,
    val currentRate: Double = 0.0,
    val hasReadings: Boolean = false,
    val monthlyAmounts: List<Pair<String, Double>> = emptyList()
)

class DashboardViewModel : ViewModel(){

    private val repository= DashboardRepository()

    private val _uiState= MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    fun loadDashboard(userId: String){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true,error=null) }
            try {
                val data=repository.getDashboard(userId)
                val periods = RetrofitClient.api.getBillingPeriods()
                val monthlyAmounts = periods
                    .sortedBy { it.periodStart }
                    .map { Pair(it.periodStart.substring(0, 7), it.totalAmount) }
                _uiState.update { it.copy(
                    isLoading = false,
                    forecastTotal = data.forecastTotal,
                    monthAmount = data.monthAmount,
                    monthConsumption = data.monthConsumption,
                    daysRemaining = data.daysRemaining,
                    recommendation = data.recommendation,
                    currentRate = data.currentRate,
                    hasReadings = data.hasReadings,
                    monthlyAmounts = monthlyAmounts
                )
                }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(isLoading = false, error = "Loading error")
                }
            }
        }
    }
}