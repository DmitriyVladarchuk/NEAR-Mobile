package com.example.near

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.near.ui.screens.RegistrationAccount
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()
        setContent {
            NEARTheme {
                Scaffold(
                    containerColor = CustomTheme.colors.background,
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    RegistrationAccount(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
