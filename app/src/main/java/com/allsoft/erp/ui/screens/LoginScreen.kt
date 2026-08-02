package com.allsoft.erp.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.allsoft.erp.ui.components.*
import com.allsoft.erp.ui.theme.*
import com.allsoft.erp.utils.Resource
import com.allsoft.erp.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: (token: String, role: String) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val focusManager = LocalFocusManager.current

    val email             by viewModel.email.collectAsStateWithLifecycle()
    val password          by viewModel.password.collectAsStateWithLifecycle()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsStateWithLifecycle()
    val emailError        by viewModel.emailError.collectAsStateWithLifecycle()
    val passwordError     by viewModel.passwordError.collectAsStateWithLifecycle()
    val loginState        by viewModel.loginState.collectAsStateWithLifecycle()

    val isLoading = loginState is Resource.Loading

    // Handle login success
    LaunchedEffect(loginState) {
        if (loginState is Resource.Success) {
            val data = (loginState as Resource.Success).data
            data?.let { onLoginSuccess(it.token, it.role) }
            viewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy900)
    ) {
        // --- Background decorative circles ---
        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = (-80).dp, y = (-120).dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Blue500.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 80.dp, y = 80.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Gold500.copy(alpha = 0.08f),
                            Color.Transparent
                        )
                    )
                )
        )

        // --- Main content ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(72.dp))

            // Logo / Brand
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Blue500, Blue400)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text  = "ERP",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize   = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color      = White
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = buildAnnotatedString {
                    append("allsoft")
                    withStyle(SpanStyle(color = Gold500)) { append(".erp") }
                },
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color      = White
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text  = "Enterprise Resource Planning",
                style = MaterialTheme.typography.bodyMedium,
                color = Gray400,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // --- Card ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(24.dp),
                colors   = CardDefaults.cardColors(
                    containerColor = Navy800
                ),
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1E3A6E),
                            Color(0xFF0F2044)
                        )
                    )
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical   = 28.dp
                    )
                ) {
                    Text(
                        text  = "Sign In",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color      = White
                        )
                    )
                    Text(
                        text     = "Access your workspace",
                        style    = MaterialTheme.typography.bodyMedium,
                        color    = Gray400,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Error Banner
                    AnimatedVisibility(
                        visible = loginState is Resource.Error,
                        enter   = fadeIn() + expandVertically(),
                        exit    = fadeOut() + shrinkVertically()
                    ) {
                        Column {
                            ErrorBanner(
                                message = (loginState as? Resource.Error)?.message ?: ""
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    // Email field
                    ERPTextField(
                        value         = email,
                        onValueChange = viewModel::onEmailChange,
                        label         = "Email Address",
                        leadingIcon   = Icons.Outlined.Email,
                        errorMessage  = emailError,
                        enabled       = !isLoading,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction    = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password field
                    ERPTextField(
                        value             = password,
                        onValueChange     = viewModel::onPasswordChange,
                        label             = "Password",
                        leadingIcon       = Icons.Outlined.Lock,
                        isPassword        = true,
                        isPasswordVisible = isPasswordVisible,
                        onPasswordToggle  = viewModel::togglePasswordVisibility,
                        errorMessage      = passwordError,
                        enabled           = !isLoading,
                        keyboardOptions   = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction    = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                viewModel.login()
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Forgot password
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick  = { /* TODO: Navigate to ForgotPassword */ },
                            enabled  = !isLoading,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Text(
                                text  = "Forgot password?",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Blue400
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Login button
                    GradientButton(
                        text      = "Sign In",
                        onClick   = {
                            focusManager.clearFocus()
                            viewModel.login()
                        },
                        modifier  = Modifier.fillMaxWidth(),
                        isLoading = isLoading,
                        enabled   = !isLoading
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Footer
            Text(
                text  = "v1.0.0  |  Powered by Allsoft",
                style = MaterialTheme.typography.labelMedium,
                color = Gray600,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
