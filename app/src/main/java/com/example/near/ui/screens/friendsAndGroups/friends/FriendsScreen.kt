package com.example.near.ui.screens.friendsAndGroups.friends

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
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.near.R
import com.example.near.domain.shared.models.UIState
import com.example.near.feature.user.domain.models.AllFriendsInfo
import com.example.near.feature.user.domain.models.User
import com.example.near.ui.components.common.AppTextField
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme


@Composable
fun FriendsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FriendsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val selectedTab by viewModel.selectedTab
    val friendsData by viewModel.friendsData
    val searchQuery by viewModel.searchQuery
    val searchResults by viewModel.searchResults

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        FriendsHeader(
            selectedTab = selectedTab,
            onTabSelected = { viewModel.selectTab(it) },
            searchQuery = searchQuery,
            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
            onClearSearch = {
                viewModel.updateSearchQuery("")
                viewModel.selectTab(FriendsTab.ALL)
            }
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
                FriendsBody(
                    friendsData = friendsData,
                    selectedTab = selectedTab,
                    searchResults = searchResults,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun FriendsHeader(
    selectedTab: FriendsTab,
    onTabSelected: (FriendsTab) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit
) {
    Column(
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
    ) {
        if (selectedTab == FriendsTab.SEARCH) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    labelRes = R.string.search_users,
                    placeholderRes = R.string.search_users_placeholder,
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        IconButton(onClick = onClearSearch) {
                            Icon(Icons.Default.Close, "Clear search")
                        }
                    }
                )
            }
        } else {
            // ... остальной код без изменений
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FriendsTabButton(
                    text = stringResource(R.string.all_friends),
                    isSelected = selectedTab == FriendsTab.ALL,
                    onClick = { onTabSelected(FriendsTab.ALL) }
                )
                Spacer(Modifier.width(8.dp))
                FriendsTabButton(
                    text = stringResource(R.string.requests),
                    isSelected = selectedTab == FriendsTab.REQUESTS,
                    onClick = { onTabSelected(FriendsTab.REQUESTS) }
                )

                Spacer(Modifier.weight(1f))

                IconButton(
                    onClick = { onTabSelected(FriendsTab.SEARCH) },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = if (selectedTab == FriendsTab.SEARCH) CustomTheme.colors.currentContainer else Color.Transparent,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = if (selectedTab == FriendsTab.SEARCH) Color.White else CustomTheme.colors.content
                    )
                }
            }
        }
    }
}

@Composable
private fun FriendsBody(
    friendsData: AllFriendsInfo?,
    selectedTab: FriendsTab,
    searchResults: List<User>,
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

        when (selectedTab) {
            FriendsTab.ALL -> {
                FriendsList(
                    friends = friendsData?.friends ?: emptyList(),
                    onItemClick = { userId -> navController.navigate("profile/$userId") }
                )
            }
            FriendsTab.REQUESTS -> {
                ReceivedRequestsList(
                    requests = friendsData?.receivedRequests ?: emptyList(),
                    onItemClick = { userId -> navController.navigate("profile/$userId") }
                )
            }
            FriendsTab.SEARCH -> {
                SearchResultsList(
                    results = searchResults,
                    onItemClick = { userId -> navController.navigate("profile/$userId") }
                )
            }
        }
    }
}

@Composable
private fun SearchResultsList(
    results: List<User>,
    onItemClick: (String) -> Unit
) {
    if (results.isNotEmpty()) {
        LazyColumn {
            items(results) { user ->
                UserSearchItem(
                    user = user,
                    onItemClick = { onItemClick(user.id) }
                )
                Divider(
                    color = CustomTheme.colors.content.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No users found",
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun UserSearchItem(
    user: User,
    onItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(user.id) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "",
            contentDescription = "user avatar",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            placeholder = painterResource(R.drawable.default_avatar),
            error = painterResource(R.drawable.default_avatar)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = "${user.firstName} ${user.lastName}",
                style = AppTypography.bodyMedium
            )
            Text(
                text = "@${user.firstName} ${user.lastName}",
                style = AppTypography.bodySmall,
                color = CustomTheme.colors.content.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun FriendsTabButton(
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
private fun FriendsList(
    friends: List<User>,
    onItemClick: (String) -> Unit
) {
    LazyColumn {
        items(friends) { friend ->
            FriendItem(
                friend = friend,
                onItemClick = { onItemClick(friend.id) }
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
private fun ReceivedRequestsList(
    requests: List<User>,
    onItemClick: (String) -> Unit
) {
    if (requests.isNotEmpty()) {
        Text(
            text = "Received Requests",
            modifier = Modifier.padding(16.dp),
            style = AppTypography.bodyMedium
        )
        LazyColumn {
            items(requests) { request ->
                FriendRequestItem(
                    friend = request,
                    onItemClick = { onItemClick(request.id) }
                )
                if (request != requests.last()) {
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
private fun FriendItem(friend: User, onItemClick: (String) -> Unit) {
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
        Column {
            Text(
                text = "${friend.firstName} ${friend.lastName}",
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content,
            )
            Text(
                text = "${friend.friends.size} friends",
                style = AppTypography.bodySmall,
                color = CustomTheme.colors.content.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun FriendRequestItem(friend: User, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick(friend.id) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text("${friend.firstName} ${friend.lastName}", style = AppTypography.bodyMedium)
            Text(stringResource(R.string.wants_to_be_your_friend), style = AppTypography.bodySmall)
        }
        Row {
            IconButton(onClick = { /* Принять запрос */ }) {
                Icon(Icons.Default.Check, "Accept")
            }
            IconButton(onClick = { /* Отклонить запрос */ }) {
                Icon(Icons.Default.Close, "Decline")
            }
        }
    }
}