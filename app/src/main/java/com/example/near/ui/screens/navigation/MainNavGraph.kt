package com.example.near.ui.screens.navigation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.near.R
import com.example.near.domain.shared.models.UIState
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
import com.example.near.ui.screens.settings.SettingsScreen
import com.example.near.ui.screens.subscriptions.SubscribersScreen
import com.example.near.ui.screens.subscriptions.SubscriptionsScreen
import com.example.near.ui.screens.templates.CreateTemplate
import com.example.near.ui.screens.templates.InfoTemplateScreen
import com.example.near.ui.theme.CustomTheme


@Composable
fun MainNavGraph(
    viewModel: NavigationViewModel = hiltViewModel(),
    startDestination: String = Routes.Onboarding.route
) {
    val navController = rememberNavController()
    val currentRoute = remember { mutableStateOf(startDestination) }
    val uiState by viewModel.uiState.collectAsState()
    val navigationRoute by viewModel.navigationRoute.collectAsState()
    val context = LocalContext.current
    var isNavHostReady by remember { mutableStateOf(false) }

    // Обработка навигации
    LaunchedEffect(navigationRoute, isNavHostReady) {
        if (isNavHostReady && navigationRoute != null) {
            navController.navigate(navigationRoute!!) {
                popUpTo(Routes.Splash.route) { inclusive = true }
            }
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is UIState.Error) {
            Toast.makeText(
                context,
                (uiState as UIState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Отслеживание текущего маршрута
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route ?: startDestination
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
                    isCommunity = viewModel.authDataStorage.getCredentials()?.isCommunity ?: false
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

            composable(Routes.Splash.route) {
                LaunchedEffect(Unit) {
                    isNavHostReady = true
                }
                when (uiState) {
                    UIState.Loading -> SplashScreen(loading = true)
                    is UIState.Error -> SplashScreen(loading = false)
                    UIState.Success -> {}
                    UIState.Idle -> SplashScreen(loading = false)
                }
            }

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
fun SplashScreen(loading: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Ваше лого
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (loading) {
                CircularProgressIndicator(color = CustomTheme.colors.orange)
            }
        }
    }
}
