package com.example.smarthome.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthome.data.api.RetrofitClient
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
    val isSuccess: Boolean = false,
    val userId: String="",
    val userName: String = "",
    val userEmail: String = ""
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
                val response = repository.login(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )
                RetrofitClient.token=response.accessToken
                _uiState.update { it.copy(
                    isSuccess = true,
                    userId=response.userId,
                    userName = response.name,
                    userEmail = response.email
                ) }
            } catch (e: Exception) {
                val errorMessage = try {
                    val gson = com.google.gson.Gson()
                    val errorBody = (e as? retrofit2.HttpException)
                        ?.response()
                        ?.errorBody()
                        ?.string()
                    if (errorBody != null) {
                        val validationError = gson.fromJson(
                            errorBody,
                            com.example.smarthome.data.model.ValidationErrorResponse::class.java
                        )
                        validationError.errors.joinToString("\n") { it.message }
                    } else {
                        "Invalid email or password"
                    }
                } catch (parseError: Exception) {
                    "Invalid email or password"
                }
                _uiState.update { it.copy(error = errorMessage) }
            }finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}