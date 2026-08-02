package com.allsoft.erp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.allsoft.erp.ui.theme.White

// Placeholder — replace with real home screen later
@Composable
fun HomeScreen(token: String, role: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text  = "Welcome to ERP Mobile",
                style = MaterialTheme.typography.headlineMedium,
                color = White
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text  = "Role: $role",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
