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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.near.R
import com.example.near.domain.models.user.UserFriend
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.MainHeaderTextInfo

@Composable
fun SubscribersScreen(
    navController: NavController,
    viewModel: SubscriberViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
    ) {
        MainHeaderTextInfo(
            text = stringResource(R.string.subscribers),
            modifier = Modifier.padding(vertical = 16.dp)
        )

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
            LazyColumn() {
                items(viewModel.subscribers ?: listOf()) { com ->
                    FriendItem(
                        com
                    ) { }
                    if (com != viewModel.subscribers?.last()) {
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
        Column {
            Text(
                text = "${friend.firstName} ${friend.lastName}",
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content,
            )
        }
    }
}