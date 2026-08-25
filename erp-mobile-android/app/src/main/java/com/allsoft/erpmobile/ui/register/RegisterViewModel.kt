package com.allsoft.erpmobile.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allsoft.erpmobile.data.repository.AuthRepository
import com.allsoft.erpmobile.data.repository.RegisterResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail, errorMessage = null)
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword, errorMessage = null)
    }

    fun onConfirmPasswordChange(newConfirm: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = newConfirm, errorMessage = null)
    }

    fun onRoleChange(newRole: String) {
        _uiState.value = _uiState.value.copy(role = newRole)
    }

    fun register() {
        val current = _uiState.value

        if (current.email.isBlank() || current.password.isBlank()) {
            _uiState.value = current.copy(errorMessage = "Tous les champs sont requis")
            return
        }

        if (current.password.length < 6) {
            _uiState.value = current.copy(errorMessage = "Le mot de passe doit faire au moins 6 caractères")
            return
        }

        if (current.password != current.confirmPassword) {
            _uiState.value = current.copy(errorMessage = "Les mots de passe ne correspondent pas")
            return
        }

        _uiState.value = current.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            when (val result = authRepository.register(current.email.trim(), current.password, current.role)) {
                is RegisterResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isRegisterSuccessful = true
                    )
                }
                is RegisterResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }
    }

    fun resetRegisterSuccessFlag() {
        _uiState.value = _uiState.value.copy(isRegisterSuccessful = false)
    }
}