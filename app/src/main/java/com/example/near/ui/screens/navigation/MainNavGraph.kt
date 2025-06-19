package com.example.near.ui.screens.navigation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.near.domain.usecase.user.auth.LoadUserUseCase
import com.example.near.ui.screens.auth.login.account.LoginAccountScreen
import com.example.near.ui.screens.auth.login.community.LoginCommunityScreen
import com.example.near.ui.screens.auth.signup.account.SignupAccountScreen
import com.example.near.ui.screens.auth.signup.community.SignupCommunityScreen
import com.example.near.ui.screens.bottomBar.BottomBar
import com.example.near.ui.screens.dashboard.community.DashboardCommunityScreen
import com.example.near.ui.screens.dashboard.user.DashboardScreen
import com.example.near.ui.screens.friendsAndGroups.FriendsAndGroupsScreen
import com.example.near.ui.screens.friendsAndGroups.FriendsAndSubscriptions
import com.example.near.ui.screens.friendsAndGroups.groups.CreateGroupsScreen
import com.example.near.ui.screens.onboarding.OnboardingScreen
import com.example.near.ui.screens.profile.community.ProfileCommunityScreen
import com.example.near.ui.screens.profile.edit.EditUserProfileScreen
import com.example.near.ui.screens.profile.user.ProfileScreen
import com.example.near.ui.screens.profile.user.ProfileViewModel
import com.example.near.ui.screens.settings.SettingsScreen
import com.example.near.ui.screens.subscriptions.SubscribersScreen
import com.example.near.ui.screens.subscriptions.SubscriptionsScreen
import com.example.near.ui.screens.templates.CreateTemplate
import com.example.near.ui.screens.templates.InfoTemplateScreen
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun MainNavGraph(
    viewModel: NavigationViewModel = hiltViewModel(),
    startDestination: String = Routes.Onboarding.route
) {
    val navController = rememberNavController()
    val isLoggedIn = viewModel.sessionManager.isLoggedIn
    val currentRoute = remember { mutableStateOf(startDestination) }

    // Следим за изменениями маршрута
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route ?: startDestination
        }
    }

    // Авторизация
    LaunchedEffect(Unit) {
        val success = withContext(Dispatchers.IO) {
            viewModel.loadUserUseCase()
        }

        if (success) {
            val isCommunity = viewModel.authDataStorage.getCredentials()?.third ?: false
            val fcmToken = viewModel.authDataStorage.getFcmToken()
            if (fcmToken == null)
                viewModel.refreshToken()

            val mainRoute = if (isCommunity) {
                fcmToken?.let { viewModel.communityRepository.sendFcmToken(it) }
                Routes.CommunityDashboard.route
            } else {
                fcmToken?.let { viewModel.userRepository.sendFcmToken(it) }
                Routes.Dashboards.route
            }
            navController.navigate(mainRoute) {
                popUpTo(0) { inclusive = true }
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
                    Routes.Profile.route,
                    Routes.CommunityDashboard.route,
                    Routes.CommunitySubscribers.route,
                    Routes.CommunityProfile.route
                )
            ) {
                BottomBar(
                    navController = navController,
                    isCommunity = viewModel.authDataStorage.getCredentials()?.third ?: false
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            val baseModifier = Modifier.padding(innerPadding)

            // --- Общие экраны  ---
            composable(Routes.Onboarding.route) {
                OnboardingScreen(
                    onAccountClick = { navController.navigate(Routes.SignupAccount.route) },
                    onCommunityClick = { navController.navigate(Routes.SignupCommunity.route) }
                )
            }
            composable(Routes.LoginAccount.route) {
                LoginAccountScreen(
                    onSignUpClick = { navController.popBackStack() },
                    navController = navController
                )
            }
            composable(Routes.SignupAccount.route) {
                SignupAccountScreen(
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

            // --- NavGraph для пользователя ---
            navigation(
                startDestination = Routes.Dashboards.route,
                route = "user_graph"
            ) {
                composable(Routes.Dashboards.route) {
                    DashboardScreen(navController = navController)
                }

                composable(Routes.Friends.route) {
                    FriendsAndGroupsScreen(navController = navController)
                }

                composable(Routes.Subscriptions.route) {
                    SubscriptionsScreen(navController = navController)
                }

                composable(
                    route = Routes.CommunityProfile.route + "/{communityId}",
                    arguments = listOf(navArgument("communityId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val communityId = backStackEntry.arguments?.getString("communityId")
                    ProfileCommunityScreen(communityId = communityId, navController = navController)
                }

                composable(Routes.Profile.route) {
                    ProfileScreen(navController = navController)
                }

                composable(Routes.EditUserProfile.route) {
                    EditUserProfileScreen(navController = navController)
                }

                composable(
                    route = "profile/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")
                    ProfileScreen(userId = userId, navController = navController)
                }

                composable(
                    route = "profile_info/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val userId = backStackEntry.arguments!!.getString("userId")!!
                    FriendsAndSubscriptions(userId = userId, navController = navController)
                }

                composable(Routes.Settings.route) {
                    SettingsScreen(navController = navController)
                }

                composable(Routes.CreateGroup.route) {
                    CreateGroupsScreen(navController)
                }

                composable(
                    route = "create_group/{groupId}",
                    arguments = listOf(navArgument("groupId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val groupId = backStackEntry.arguments?.getString("groupId")
                    CreateGroupsScreen(groupId = groupId, navController = navController)
                }

                // --- Template ---
                composable(Routes.CreateTemplate.route) {
                    CreateTemplate(navController = navController)
                }

                composable(
                    route = "edit_template/{templateId}",
                    arguments = listOf(navArgument("templateId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val templateId = backStackEntry.arguments?.getString("templateId")
                    CreateTemplate(templateId = templateId, navController = navController)
                }

                composable(
                    route = Routes.TemplateInfo.route + "/{templateId}",
                    arguments = listOf(navArgument("templateId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val templateId = backStackEntry.arguments?.getString("templateId")
                    InfoTemplateScreen(templateId = templateId!!, navController = navController)
                }
            }

            // --- NavGraph для сообщества ---
            navigation(
                startDestination = Routes.CommunityDashboard.route,
                route = "community_graph"
            ) {
                composable(Routes.CommunityDashboard.route) {
                    DashboardCommunityScreen(navController = navController)
                }
                composable(Routes.CommunitySubscribers.route) {
                    //TestScreen("Subscribers")
                    SubscribersScreen(navController = navController)
                }
                composable(Routes.CommunityProfile.route) { ProfileCommunityScreen(navController = navController) }

                // --- Template ---
                composable(Routes.CreateTemplate.route) {
                    CreateTemplate(navController = navController, isCommunity = true)
                }
//                composable(
//                    route = Routes.TemplateInfo.route + "/{templateId}",
//                    arguments = listOf(navArgument("templateId") { type = NavType.StringType })
//                ) { backStackEntry ->
//                    val templateId = backStackEntry.arguments?.getString("templateId") ?: ""
//                    InfoTemplateScreen(isCommunity = true, templateId = templateId!!, navController = navController)
//                }

                composable(
                    route = Routes.TemplateCommunityInfo.route + "/{templateId}",
                    arguments = listOf(navArgument("templateId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val templateId = backStackEntry.arguments?.getString("templateId") ?: ""
                    //InfoTemplateScreen(isCommunity = true, templateId = templateId!!, navController = navController)
                }
            }
        }
    }
}

@Composable
private fun TestScreen(text: String, profileViewModel: ProfileViewModel = hiltViewModel()) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = AppTypography.titleLarge,
            color = CustomTheme.colors.content
        )
        Text(
            text = "LogOut",
            style = AppTypography.titleMedium,
            color = CustomTheme.colors.content,
            modifier = Modifier.padding(top = 16.dp).clickable {
                profileViewModel.logOut()
            }
        )
    }
}
