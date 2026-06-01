package com.example.smarthome.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String="",
    val password:String="",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class LoginViewModel: ViewModel(){
    private val repository= AuthRepository()

    private val _uiState= MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(email:String){
        _uiState.update { it.copy(email=email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun login(){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                repository.login(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
                _uiState.update { it.copy(isSuccess = true) }
            }catch (e: Exception){
                _uiState.update {
                    it.copy(error = "Неыерный эмайл или пароль")
                }
            }finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}