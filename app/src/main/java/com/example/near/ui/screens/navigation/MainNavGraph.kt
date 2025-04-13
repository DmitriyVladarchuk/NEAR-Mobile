package com.example.near.ui.screens.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.near.ui.screens.auth.login.account.LoginAccountScreen
import com.example.near.ui.screens.auth.login.community.LoginCommunityScreen
import com.example.near.ui.screens.auth.signup.account.SignupAccountScreen
import com.example.near.ui.screens.auth.signup.community.SignupCommunityScreen
import com.example.near.ui.screens.bottomBar.BottomBar
import com.example.near.ui.screens.dashboard.DashboardScreen
import com.example.near.ui.screens.friends.FriendsScreen
import com.example.near.ui.screens.onboarding.OnboardingScreen
import com.example.near.ui.screens.profile.ProfileScreen
import com.example.near.ui.screens.subscriptions.SubscriptionsScreen
import com.example.near.ui.theme.CustomTheme

@Composable
fun MainNavGraph(
    startDestination: String = Routes.Onboarding.route
) {
    val navController = rememberNavController()
    val currentRoute = remember { mutableStateOf(startDestination) }

    // Следим за изменениями маршрута
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route ?: startDestination
        }
    }

    Scaffold(
        containerColor = CustomTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Показываем BottomBar только на основных экранах
            if (currentRoute.value in listOf(
                    Routes.Dashboards.route,
                    Routes.Friends.route,
                    Routes.Subscriptions.route,
                    Routes.Profile.route
                )) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            val baseModifier = Modifier.padding(innerPadding)
            composable(Routes.Onboarding.route) {
                OnboardingScreen(
                    modifier = baseModifier,
                    onAccountClick = { navController.navigate(Routes.SignupAccount.route) },
                    onCommunityClick = { navController.navigate(Routes.SignupCommunity.route) }
                )
            }
            composable(Routes.LoginAccount.route) {
                LoginAccountScreen(
                    modifier = baseModifier,
                    onSignUpClick = { navController.popBackStack() },
                    navController = navController
                )
            }
            composable(Routes.SignupAccount.route) {
                SignupAccountScreen(
                    modifier = baseModifier,
                    onLoginClick = { navController.navigate(Routes.LoginAccount.route) },
                    navController = navController
                )
            }

            composable(Routes.LoginCommunity.route) {
                LoginCommunityScreen(
                    modifier = baseModifier,
                    onSignUpClick = { navController.popBackStack() },
                    navController = navController
                )
            }
            composable(Routes.SignupCommunity.route) {
                SignupCommunityScreen(
                    modifier = baseModifier,
                    onLoginClick = { navController.navigate(Routes.LoginCommunity.route) },
                    navController = navController
                )
            }

            // Main App (заглушка)
            composable(Routes.Main.route) {
                Text("Main Screen", modifier = Modifier.fillMaxSize())
            }

            // Экран Dashboard
            composable(Routes.Dashboards.route) {
                DashboardScreen(modifier = Modifier.padding(innerPadding))
            }

            // Экран Friends
            composable(Routes.Friends.route) {
                FriendsScreen(modifier = Modifier.padding(innerPadding))
            }

            // Экран Notifications
            composable(Routes.Subscriptions.route) {
                SubscriptionsScreen(modifier = Modifier.padding(innerPadding))
            }

            // Экран Profile
            composable(Routes.Profile.route) {
                ProfileScreen()
            }
        }
    }

}