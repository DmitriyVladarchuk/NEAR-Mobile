package com.example.near.ui.screens.subscriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.near.domain.shared.models.UIState
import com.example.near.domain.user.models.UserSubscription
import com.example.near.ui.components.common.AppTextField
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.components.headers.MainHeaderTextInfo

@Composable
fun SubscriptionsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SubscriptionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val selectedTab by viewModel.selectedTab
    val communities by viewModel.communities
    val searchQuery by viewModel.searchQuery

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        MainHeaderTextInfo(
            text = stringResource(R.string.subscriptions),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        CommunitiesTabs(
            selectedTab = selectedTab,
            searchQuery = searchQuery,
            onTabSelected = { viewModel.selectTab(it) },
            onSearchQueryChanged = { viewModel.updateSearchQuery(it) }
        )

        when (uiState) {
            is UIState.Idle -> Unit
            is UIState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UIState.Error -> {
                val error = (uiState as UIState.Error).message
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error: $error", color = Color.Red)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.loadInitialData() }) {
                        Text("Retry")
                    }
                }
            }

            is UIState.Success -> {
                CommunitiesList(
                    communities = communities,
                    navController = navController,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun CommunitiesTabs(
    selectedTab: CommunityTab,
    searchQuery: String,
    onTabSelected: (CommunityTab) -> Unit,
    onSearchQueryChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CustomTheme.colors.container_2,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (selectedTab != CommunityTab.SEARCH) {
            // Показываем обычные табы, если не выбран поиск
            TabButton(
                text = stringResource(R.string.subscriptions),
                isSelected = selectedTab == CommunityTab.SUBSCRIPTION,
                onClick = { onTabSelected(CommunityTab.SUBSCRIPTION) }
            )

            TabButton(
                text = stringResource(R.string.all_community),
                isSelected = selectedTab == CommunityTab.ALL,
                onClick = { onTabSelected(CommunityTab.ALL) }
            )

            IconButton(
                onClick = { onTabSelected(CommunityTab.SEARCH) },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (selectedTab == CommunityTab.SEARCH) CustomTheme.colors.currentContainer
                        else Color.Transparent,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = if (selectedTab == CommunityTab.SEARCH) Color.White
                    else CustomTheme.colors.content
                )
            }
        } else {
            AppTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                labelRes = R.string.search_communities,
                placeholderRes = R.string.search_communities_placeholder,
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onSearchQueryChanged("")
                            onTabSelected(CommunityTab.SUBSCRIPTION)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close search",
                            tint = CustomTheme.colors.content
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun TabButton(
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
private fun CommunitiesList(
    communities: List<UserSubscription>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(communities) { community ->
            CommunityItem(
                community = community,
                onItemClick = { navController.navigate(Routes.CommunityProfile.route + "/${community.id}") }
            )
            if (community != communities.last()) {
                Divider(
                    color = CustomTheme.colors.content,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun CommunityItem(community: UserSubscription, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
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