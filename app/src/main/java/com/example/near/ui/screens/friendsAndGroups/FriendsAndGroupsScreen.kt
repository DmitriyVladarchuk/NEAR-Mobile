package com.example.near.ui.screens.friendsAndGroups

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.ui.screens.friendsAndGroups.friends.FriendsScreen
import com.example.near.ui.screens.friendsAndGroups.groups.GroupsScreen
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.DynamicItemContainer
import com.example.near.ui.views.MainHeaderTextInfo

@Composable
fun FriendsAndGroupsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val tabs = listOf(
        stringResource(R.string.friends),
        stringResource(R.string.groups)
    )
    var selectedTab by remember { mutableStateOf(tabs[0]) }
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = tabs[pagerState.currentPage]
    }

    LaunchedEffect(selectedTab) {
        val page = tabs.indexOf(selectedTab)
        if (page != pagerState.currentPage) {
            pagerState.animateScrollToPage(page)
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
    ) {
        MainHeaderTextInfo(
            text = stringResource(
                when (selectedTab) {
                    "Friends" -> R.string.friends
                    "Groups" -> R.string.groups
                    else -> R.string.friends
                }
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        DynamicItemContainer(
            items = tabs,
            selectedItem = selectedTab,
            onItemSelected = { tab -> selectedTab = tab },
            modifier = Modifier.padding(bottom = 16.dp)
        ) { item, isSelected, modifier, onClick ->
            TabItem(
                text = item,
                isSelected = isSelected,
                modifier = modifier,
                onClick = { onClick(item) }
            )
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = true,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> FriendsScreen(navController = navController)
                1 -> GroupsScreen(navController = navController)
            }
        }
    }
}

@Composable
private fun TabItem(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = if (isSelected) AppTypography.titleMedium else AppTypography.bodyMedium,
            color = if (isSelected) CustomTheme.colors.content else CustomTheme.colors.content.copy(alpha = 0.6f)
        )
    }
}