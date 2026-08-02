package com.allsoft.erp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.allsoft.erp.navigation.ERPNavGraph
import com.allsoft.erp.ui.theme.ERPMobileTheme
import com.allsoft.erp.ui.theme.Navy900


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ERPMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = Navy900
                ) {
                    ERPNavGraph(navController = rememberNavController())
                }
            }
        }
    }
}
