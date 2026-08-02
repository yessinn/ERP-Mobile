package com.allsoft.erp.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.allsoft.erp.ui.theme.*

@Composable
fun ERPTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onPasswordToggle: (() -> Unit)? = null,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true
) {
    val hasError = errorMessage != null

    Column(modifier = modifier) {
        Text(
            text  = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (hasError) Error else Gray400,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        OutlinedTextField(
            value         = value,
            onValueChange = onValueChange,
            modifier      = Modifier.fillMaxWidth(),
            enabled       = enabled,
            singleLine    = true,
            shape         = RoundedCornerShape(12.dp),
            visualTransformation = if (isPassword && !isPasswordVisible)
                PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = if (hasError) Error else Gray400,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            trailingIcon = when {
                isPassword -> {
                    {
                        IconButton(onClick = { onPasswordToggle?.invoke() }) {
                            Icon(
                                imageVector = if (isPasswordVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                                contentDescription = if (isPasswordVisible)
                                    "Hide password" else "Show password",
                                tint = Gray400,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                trailingIcon != null -> trailingIcon
                else -> null
            },
            isError = hasError,
            colors  = OutlinedTextFieldDefaults.colors(
                focusedBorderColor     = Blue500,
                unfocusedBorderColor   = Color(0xFF1E2F50),
                errorBorderColor       = Error,
                focusedContainerColor  = Navy800,
                unfocusedContainerColor= Navy800,
                errorContainerColor    = Color(0xFF1A1020),
                focusedTextColor       = White,
                unfocusedTextColor     = White,
                errorTextColor         = White,
                cursorColor            = Blue400,
                focusedLeadingIconColor   = Blue400,
                unfocusedLeadingIconColor = Gray400
            )
        )

        AnimatedVisibility(
            visible = hasError,
            enter   = fadeIn() + expandVertically(),
            exit    = fadeOut() + shrinkVertically()
        ) {
            Text(
                text     = errorMessage ?: "",
                style    = MaterialTheme.typography.labelMedium,
                color    = Error,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick  = onClick,
        enabled  = enabled && !isLoading,
        modifier = modifier.height(54.dp),
        shape    = RoundedCornerShape(12.dp),
        colors   = ButtonDefaults.buttonColors(
            containerColor         = Blue500,
            disabledContainerColor = Navy700
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation  = 4.dp,
            pressedElevation  = 8.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier  = Modifier.size(22.dp),
                    color     = White,
                    strokeWidth = 2.5.dp
                )
            } else {
                Text(
                    text       = text,
                    style      = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = White
                )
            }
        }
    }
}

@Composable
fun ErrorBanner(message: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF2D1515),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 1.dp,
                color = Error.copy(alpha = 0.4f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text  = "Error",
            style = MaterialTheme.typography.labelMedium,
            color = Error,
            fontWeight = FontWeight.Bold
        )
        Text(
            text  = message,
            style = MaterialTheme.typography.bodyMedium,
            color = Error.copy(alpha = 0.9f)
        )
    }
}
