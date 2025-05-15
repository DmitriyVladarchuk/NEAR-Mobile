package com.example.near.ui.screens.friendsAndGroups.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.example.near.domain.models.UserGroup
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme

@Composable
fun GroupsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: GroupsViewModel = hiltViewModel()
) {
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
                GroupsBody(
                    groups = viewModel.groups,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun GroupsBody(
    groups: List<UserGroup>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .background(
                color = CustomTheme.colors.container_2,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        if (groups.isEmpty()) {
            ButtonCreateGroups { navController.navigate(Routes.CreateGroup.route) }
        }
        LazyColumn() {
            items(groups) { group ->
                GroupItem(
                    group = group,
                    onItemClick = { groupId ->
                        navController.navigate("create_group/$groupId")
                    }
                )
                Spacer(
                    Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(CustomTheme.colors.content)
                )
                if (group == groups.last()) {
                    ButtonCreateGroups { navController.navigate(Routes.CreateGroup.route) }
                }
            }
        }
    }
}

@Composable
private fun GroupItem(group: UserGroup, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { onItemClick(group.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "",
            contentDescription = "group avatar",
            modifier = Modifier
                .size(48.dp)
                .aspectRatio(1f)
                .padding(end = 8.dp),
            placeholder = painterResource(R.drawable.default_avatar),
            error = painterResource(R.drawable.default_avatar)
        )
        Text(
            text = group.groupName,
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content,
        )
    }
}

@Composable
private fun ButtonCreateGroups(onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 16.dp).clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Create new groups",
            modifier = Modifier
                .border(1.dp, CustomTheme.colors.content, RoundedCornerShape(8.dp))
                .size(32.dp),
            tint = CustomTheme.colors.content
        )
        Text(
            text = stringResource(R.string.create_new_groups),
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}