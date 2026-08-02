package com.allsoft.erp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allsoft.erp.data.model.LoginResponse
import com.allsoft.erp.data.repository.AuthRepository
import com.allsoft.erp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()

    // --- UI State ---
    private val _loginState = MutableStateFlow<Resource<LoginResponse>>(Resource.Idle())
    val loginState: StateFlow<Resource<LoginResponse>> = _loginState.asStateFlow()

    // --- Form Fields ---
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    // --- Event Handlers ---
    fun onEmailChange(value: String) {
        _email.value = value
        _emailError.value = null
    }

    fun onPasswordChange(value: String) {
        _password.value = value
        _passwordError.value = null
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun login() {
        if (!validate()) return

        viewModelScope.launch {
            _loginState.value = Resource.Loading()
            _loginState.value = repository.login(
                email = _email.value.trim(),
                password = _password.value
            )
        }
    }

    fun resetState() {
        _loginState.value = Resource.Idle()
    }

    private fun validate(): Boolean {
        var valid = true

        if (_email.value.isBlank()) {
            _emailError.value = "Email is required"
            valid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value.trim()).matches()) {
            _emailError.value = "Enter a valid email address"
            valid = false
        }

        if (_password.value.isBlank()) {
            _passwordError.value = "Password is required"
            valid = false
        } else if (_password.value.length < 6) {
            _passwordError.value = "Password must be at least 6 characters"
            valid = false
        }

        return valid
    }
}
