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
    startDestination: String = Routes.ONBOARDING
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                modifier = modifier,
                onAccountClick = { navController.navigate(Routes.SIGNUP_ACCOUNT) },
                onCommunityClick = { navController.navigate(Routes.SIGNUP_COMMUNITY) }
            )
        }
        composable(Routes.LOGIN_ACCOUNT) {
            LoginAccountScreen(
                modifier = modifier,
                onSignUpClick = { navController.popBackStack() }
            )
        }
        composable(Routes.SIGNUP_ACCOUNT) {
            SignupAccountScreen(
                modifier = modifier,
                onLoginClick = { navController.navigate(Routes.LOGIN_ACCOUNT) },
            )
        }

        composable(Routes.LOGIN_COMMUNITY) {
            LoginCommunityScreen(
                modifier = modifier,
                onSignUpClick = { navController.popBackStack() }
            )
        }
        composable(Routes.SIGNUP_COMMUNITY) {
            SignupCommunityScreen(
                modifier = modifier,
                onLoginClick = { navController.navigate(Routes.LOGIN_COMMUNITY) },
            )
        }

        // Main App (заглушка)
        composable(Routes.MAIN) {
            Text("Main Screen", modifier = Modifier.fillMaxSize())
        }
    }
}