package com.example.near.ui.screens.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MainNavGraph(
    viewModel: NavigationViewModel = hiltViewModel(),
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

    // Автоматический логин при наличии сохраненных данных
    LaunchedEffect(Unit) {
        if (!viewModel.sessionManager.isLoggedIn) {
            viewModel.authDataStorage.getCredentials()?.let { (email, password) ->
                val result = withContext(Dispatchers.IO) {
                    viewModel.userRepository.login(email, password)
                }
                if (result.isSuccess) {
                    viewModel.sessionManager.saveAuthToken(result.getOrNull()!!)
                    navController.navigate(Routes.Dashboards.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }

    Scaffold(
        containerColor = CustomTheme.colors.background,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
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
                //SubscriptionsScreen(modifier = Modifier.padding(innerPadding))
                ProfileScreen("f8f812d3-eb1a-4e34-8fd7-c640de4fbb41")
            }

            // Экран Profile
            composable(Routes.Profile.route) {
                ProfileScreen()
            }
        }
    }

}