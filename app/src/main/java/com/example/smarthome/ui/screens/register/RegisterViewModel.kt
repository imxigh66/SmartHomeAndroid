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
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null

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
    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun register(){

        if (_uiState.value.password != _uiState.value.confirmPassword) {
            _uiState.update { it.copy(error = "Passwords do not match") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }


            try {
                repository.register(
                    name = _uiState.value.name,
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
                _uiState.update {it.copy(isSuccess = true) }
            } catch (e: Exception) {
                try {
                    val gson = com.google.gson.Gson()
                    val errorBody = (e as? retrofit2.HttpException)
                        ?.response()
                        ?.errorBody()
                        ?.string()
                    val validationError = gson.fromJson(
                        errorBody,
                        com.example.smarthome.data.model.ValidationErrorResponse::class.java
                    )
                    _uiState.update {
                        it.copy(
                            nameError = validationError.errors.firstOrNull { e -> e.field == "Name" }?.message,
                            emailError = validationError.errors.firstOrNull { e -> e.field == "Email" }?.message,
                            passwordError = validationError.errors.firstOrNull { e -> e.field == "Password" }?.message
                        )
                    }
                } catch (parseError: Exception) {
                    _uiState.update { it.copy(error = "Registration failed") }
                }
            }
                finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}