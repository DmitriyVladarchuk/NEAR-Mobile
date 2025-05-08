package com.example.near.ui.screens.bottomBar

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.DynamicItemContainer

@Composable
fun BottomBar(navController: NavController, isCommunity: Boolean) {
    val bottomBarItems = listOf(
        BottomBarItem(Routes.Dashboards.route, R.string.dashboard, Icons.Default.Home),
        BottomBarItem(Routes.Friends.route, R.string.friends, Icons.Default.Group),
        BottomBarItem(Routes.Subscriptions.route, R.string.subscriptions, Icons.Default.Notifications),
        BottomBarItem(Routes.Profile.route, R.string.my_profile, Icons.Default.AccountCircle)
    )

    // Состояние для текущего маршрута
    val currentRoute = remember { mutableStateOf(navController.currentDestination?.route ?: Routes.Dashboards.route) }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route ?: Routes.Dashboards.route
        }
    }

    DynamicItemContainer(
        items = bottomBarItems,
        selectedItem = bottomBarItems.find { it.route == currentRoute.value } ?: bottomBarItems[0],
        modifier = Modifier.padding(start = 20.dp, top = 0.dp, end = 20.dp, bottom = 20.dp),
        onItemSelected = { selected ->
            navController.navigate(selected.route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        itemContent = { bottomBarItem, isSelected, modifier, onItemSelected ->
            BottomBarItem(
                bottomItem = bottomBarItem,
                isSelected = isSelected,
                modifier = modifier,
                changeType = { onItemSelected(bottomBarItem) }
            )
        }
    )
}

@Composable
fun BottomBarItem(bottomItem: BottomBarItem, isSelected: Boolean, modifier: Modifier, changeType: () -> Unit) {
    val currentColor = CustomTheme.colors.container_2
    val containerColor = CustomTheme.colors.currentContainer

    val animateColor = remember { Animatable(containerColor) }

    LaunchedEffect(isSelected) {
        animateColor.animateTo(
            targetValue = if (isSelected) containerColor else currentColor,
            animationSpec = tween(durationMillis = 700)
        )
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(CircleShape)
            .background(
                animateColor.value
            )
            .clickable { changeType() }
            .padding(16.dp)
            .animateContentSize()
    ) {
        Icon(
            imageVector = bottomItem.icon,
            modifier = Modifier.size(32.dp),
            tint = if (isSelected) Color.White else CustomTheme.colors.content,
            //tint = CustomTheme.colors.content,
            contentDescription = ""
        )

    }
}