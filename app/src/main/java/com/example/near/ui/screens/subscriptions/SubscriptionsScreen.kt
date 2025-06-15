package com.example.near.ui.screens.subscriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.near.R
import com.example.near.domain.models.user.UserSubscription
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.MainHeaderTextInfo

@Composable
fun SubscriptionsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
    ) {
        MainHeaderTextInfo(
            text = stringResource(R.string.subscriptions),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            when {
                viewModel.isLoading -> {
                    CircularProgressIndicator()
                }
                viewModel.error != null -> {
                    Text("Error: ${viewModel.error}", color = Color.Red)
                    Button(onClick = { viewModel.loadFriends() }) {
                        Text("Retry")
                    }
                }
                else -> {
                    SubscriptionsHeader(
                        selectedTab = viewModel.selectedTab,
                        onTabSelected = { tab -> viewModel.selectTab(tab) }
                    )
                    CommunityBody(
                        community = viewModel.subscriptions,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun SubscriptionsHeader(
    selectedTab: CommunityTab,
    onTabSelected: (CommunityTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CustomTheme.colors.container_2,
                shape = RoundedCornerShape(
                    topStart = 8.dp,
                    topEnd = 8.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .padding(top = 16.dp)
            .padding(horizontal = 8.dp),
    ) {
        SubscriptionsTabButton(
            text = stringResource(R.string.subscriptions),
            isSelected = selectedTab == CommunityTab.SUBSCRIPTION,
            onClick = { onTabSelected(CommunityTab.SUBSCRIPTION) }
        )
        Spacer(Modifier.width(8.dp))
        SubscriptionsTabButton(
            text = stringResource(R.string.all_community),
            isSelected = selectedTab == CommunityTab.ALL,
            onClick = { onTabSelected(CommunityTab.ALL) }
        )

        Spacer(Modifier.weight(1f))

        IconButton(
            onClick = { onTabSelected(CommunityTab.SEARCH) },
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = if (selectedTab == CommunityTab.SEARCH) CustomTheme.colors.currentContainer else Color.Transparent,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = if (selectedTab == CommunityTab.SEARCH) Color.White else CustomTheme.colors.content
            )
        }
    }
}

@Composable
private fun SubscriptionsTabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val currentContainerColor = CustomTheme.colors.currentContainer
    val containerColor = CustomTheme.colors.container_2
    val currentContentColor = CustomTheme.colors.currentContent
    val contentColor = CustomTheme.colors.content
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) currentContainerColor else containerColor,
            contentColor = if (isSelected) currentContentColor else contentColor
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(text)
    }
}

@Composable
private fun CommunityBody(
    community: List<UserSubscription>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .background(
                color = CustomTheme.colors.container_2,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                )
            )
    ) {
        Spacer(
            Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp, bottom = 16.dp)
                .fillMaxWidth()
                .height(2.dp)
                .background(CustomTheme.colors.content)
        )
        LazyColumn() {
            items(community) { com ->
                CommunityItem(
                    community = com,
                    onItemClick = { communityId ->
                        navController.navigate(Routes.CommunityProfile.route + "/$communityId")
                    }
                )
                if (com != community.last()) {
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