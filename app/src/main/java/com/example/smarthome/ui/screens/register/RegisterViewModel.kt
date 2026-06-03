package com.example.smarthome.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RegisterUiSTate(
    val name:String="",
    val email: String="",
    val password:String="",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false

)

class RegisterViewModel: ViewModel(){
    private val repository= AuthRepository()
    private val _uiState= MutableStateFlow(RegisterUiSTate())
    val uiState: StateFlow<RegisterUiSTate> = _uiState

    fun onNameChange(name: String){
        _uiState.update { it.copy(name=name) }
    }
    fun onEmailChange(email: String){
        _uiState.update { it.copy(email=email) }
    }
    fun onPasswordChange(password: String){
        _uiState.update { it.copy(password=password) }
    }

    fun register(){

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                repository.register(
                    name = _uiState.value.name,
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
                _uiState.update {it.copy(isSuccess = true) }
            }catch (e: Exception){
                _uiState.update { it.copy(error="Ошибка регистрации") }
            }finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}