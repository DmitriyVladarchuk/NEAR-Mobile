package com.example.near

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.near.ui.screens.navigation.MainNavGraph
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.NEARTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()
        setContent {
            NEARTheme {
                MainNavGraph(
                    startDestination = Routes.Onboarding.route
                )
            }
        }
    }
}
