package com.example.near.ui.screens.navigation

sealed class Routes(val route: String) {
    object Onboarding : Routes("onboarding")
    object LoginAccount : Routes("login/account")
    object LoginCommunity : Routes("login/community")
    object SignupAccount : Routes("signup/account")
    object SignupCommunity : Routes("signup/community")
    object Main : Routes("main")
    object Dashboards : Routes("dashboards")
    object Friends : Routes("friends")
    object Subscriptions : Routes("subscriptions")
    object Profile : Routes("profile")
    object Settings : Routes("settings")
    object CreateGroup : Routes("create_group")
}