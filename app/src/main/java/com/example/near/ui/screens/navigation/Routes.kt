package com.example.near.ui.screens.navigation

sealed class Routes(val route: String) {
    // --- Общие маршруты ---
    object Onboarding : Routes("onboarding")
    object LoginAccount : Routes("login/account")
    object LoginCommunity : Routes("login/community")
    object SignupAccount : Routes("signup/account")
    object SignupCommunity : Routes("signup/community")

    // --- Маршруты для пользователя ---
    object Dashboards : Routes("dashboards")
    object Friends : Routes("friends")
    object Subscriptions : Routes("subscriptions")
    object Profile : Routes("profile")
    object Settings : Routes("settings")
    object CreateGroup : Routes("create_group")
    object CreateTemplate : Routes("create_template")
    object TemplateInfo: Routes("info_template")

    // --- Маршруты для сообщества ---
    object CommunityDashboard : Routes("community_dashboard")
    object CommunitySubscribers : Routes("community_Subscribers")
    object CommunityProfile : Routes("community_profile")
    object TemplateCommunityInfo: Routes("community_info_template")
}