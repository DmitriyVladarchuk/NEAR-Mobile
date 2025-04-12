package com.example.near.ui.screens.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.near.ui.screens.auth.login.account.LoginAccountScreen
import com.example.near.ui.screens.auth.login.community.LoginCommunityScreen
import com.example.near.ui.screens.auth.signup.account.SignupAccountScreen
import com.example.near.ui.screens.auth.signup.community.SignupCommunityScreen
import com.example.near.ui.screens.onboarding.OnboardingScreen

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    startDestination: String = Routes.Onboarding.route
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.Onboarding.route) {
            OnboardingScreen(
                modifier = modifier,
                onAccountClick = { navController.navigate(Routes.SignupAccount.route) },
                onCommunityClick = { navController.navigate(Routes.SignupCommunity.route) }
            )
        }
        composable(Routes.LoginAccount.route) {
            LoginAccountScreen(
                modifier = modifier,
                onSignUpClick = { navController.popBackStack() }
            )
        }
        composable(Routes.SignupAccount.route) {
            SignupAccountScreen(
                modifier = modifier,
                onLoginClick = { navController.navigate(Routes.LoginAccount.route) },
            )
        }

        composable(Routes.LoginCommunity.route) {
            LoginCommunityScreen(
                modifier = modifier,
                onSignUpClick = { navController.popBackStack() }
            )
        }
        composable(Routes.SignupCommunity.route) {
            SignupCommunityScreen(
                modifier = modifier,
                onLoginClick = { navController.navigate(Routes.LoginCommunity.route) },
            )
        }

        // Main App (заглушка)
        composable(Routes.Main.route) {
            Text("Main Screen", modifier = Modifier.fillMaxSize())
        }
    }
}