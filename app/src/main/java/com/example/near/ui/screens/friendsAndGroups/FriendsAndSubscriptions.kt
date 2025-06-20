package com.example.near.ui.screens.friendsAndGroups

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.near.R
import com.example.near.domain.user.models.UserFriend
import com.example.near.domain.user.models.UserSubscription
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.DynamicItemContainer
import com.example.near.ui.views.SecondaryHeaderTextInfo

@Composable
fun FriendsAndSubscriptions(
    userId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: FriendsAndSubscriptionsViewModel = hiltViewModel()
) {
    val tabs = listOf(stringResource(R.string.friends), stringResource(R.string.subscriptions))
    var selectedTab by remember { mutableStateOf(tabs[0]) }
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    LaunchedEffect(Unit) {
        viewModel.loadData(userId)
    }

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
        SecondaryHeaderTextInfo (
            text = "",
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            navController.popBackStack()
        }

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
        ) { page ->
            when (page) {
                0 -> FriendsForUser(friends = viewModel.friends, navController = navController)
                1 -> SubscriptionsForUser(subscriptions = viewModel.subscriptions, navController = navController)
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

@Composable
private fun FriendsForUser(friends: List<UserFriend>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.background(
            color = CustomTheme.colors.container_2,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        items(friends) { friend ->
            FriendItem(
                friend = friend,
                onItemClick = { userId ->
                    navController.navigate("profile/$userId")
                }
            )
            if (friend != friends.last()) {
                Spacer(
                    Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(CustomTheme.colors.content)
                )
            }
        }
    }
}

@Composable
private fun FriendItem(friend: UserFriend, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { onItemClick(friend.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "",
            contentDescription = "user avatar",
            modifier = Modifier
                .size(48.dp)
                .aspectRatio(1f)
                .padding(end = 8.dp),
            placeholder = painterResource(R.drawable.default_avatar),
            error = painterResource(R.drawable.default_avatar)
        )
        Text(
            text = "${friend.firstName} ${friend.lastName}",
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content,
        )
    }
}

@Composable
private fun SubscriptionsForUser(subscriptions: List<UserSubscription>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.background(
            color = CustomTheme.colors.container_2,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        items(subscriptions) { sub ->
            CommunityItem(
                community = sub,
                onItemClick = { userId ->
                    navController.navigate(Routes.CommunityProfile.route + "/${sub.id}")
                }
            )
            if (sub != subscriptions.last()) {
                Spacer(
                    Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(CustomTheme.colors.content)
                )
            }
        }
    }
}

@Composable
private fun CommunityItem(community: UserSubscription, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { onItemClick(community.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "",
            contentDescription = "community avatar",
            modifier = Modifier
                .size(48.dp)
                .aspectRatio(1f)
                .padding(end = 8.dp),
            placeholder = painterResource(R.drawable.near_community),
            error = painterResource(R.drawable.near_community)
        )
        Text(
            text = community.communityName,
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content,
        )
    }
}