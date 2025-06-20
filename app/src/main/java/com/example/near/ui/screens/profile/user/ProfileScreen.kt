package com.example.near.ui.screens.profile.user

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.near.R
import com.example.near.domain.shared.models.SignupNotificationOption
import com.example.near.domain.user.models.User
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme
import com.example.near.ui.theme.dark_content
import com.example.near.ui.theme.light_container
import com.example.near.ui.views.SecondaryHeaderTextInfo

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userId: String? = null,
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    var showLogout by remember { mutableStateOf(false) }
    var scrollOffset by remember { mutableFloatStateOf(0f) }
    var maxScrollOffset by remember { mutableFloatStateOf(0f) }
    val scrollThreshold = 100.dp


    LaunchedEffect(Unit) {
        if (userId == null) {
            viewModel.loadUser()
        } else {
            viewModel.loadUser(userId)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.logoutEvent.collect {
            navController.navigate(Routes.Onboarding.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    scrollOffset += dragAmount
                    maxScrollOffset = maxOf(maxScrollOffset, scrollOffset)
                    // Показываем кнопки при скролле вниз
                    if (dragAmount < 0) {
                        showLogout = true
                    }
                    // Прячем при скролле вверх
                    else if (scrollOffset > maxScrollOffset - scrollThreshold.toPx()) {
                        showLogout = false
                    }
                }
            }
    ) {
        if (userId != null) {
            SecondaryHeaderTextInfo(
                text = stringResource(R.string.profile),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                navController.popBackStack()
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Box(modifier = Modifier.fillMaxWidth().weight(0.3f)) {
                UserAvatarSection(
                    avatarUrl = viewModel.avatarUrl,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().weight(0.2f)) {
                viewModel.user?.let { user ->
                    if (userId != null)
                        FriendsAndSubscription(
                            user = user,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable { navController.navigate("profile_info/$userId") }
                        )
                    else
                        FriendsAndSubscription(
                            user = user,
                            modifier = Modifier.fillMaxSize()
                        )
                }
            }
            Spacer(modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
                viewModel.user?.let { user ->
                    UserProfileCard(
                        userId = userId != null,
                        navController = navController,
                        user = user,
                        friendshipStatus = viewModel.friendshipStatus,
                        onAccept = { userId?.let { viewModel.addFriend(it) } },
                        onReject = { userId?.let { viewModel.rejectFriend(it) } },
                        onAction = { userId?.let { viewModel.handleFriendshipAction(it) } },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Spacer(modifier.height(8.dp))
        }

        AnimatedVisibility(
            visible = showLogout,
            enter = fadeIn() + slideInVertically { it },
            exit = fadeOut() + slideOutVertically { it },
            modifier = Modifier.fillMaxWidth()
        ) {
            SettingAndLogOut(
                settingClick = { navController.navigate(Routes.Settings.route) },
                logOutClick = { viewModel.logOut() },
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}

@Composable
private fun UserAvatarSection(avatarUrl: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "user avatar",
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .aspectRatio(1f),
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(R.drawable.default_avatar),
            error = painterResource(R.drawable.default_avatar)
        )
    }
}

@Composable
private fun UserProfileCard(
    modifier: Modifier = Modifier,
    userId: Boolean = false,
    navController: NavController,
    user: User,
    friendshipStatus: FriendshipStatus,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onAction: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = CustomTheme.colors.container_2, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
        Text(
            text = "${user.firstName} ${user.lastName}",
            style = AppTypography.titleMedium,
            color = CustomTheme.colors.content,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(2.dp).background(CustomTheme.colors.content))

        InfoRowForUser(Icons.Filled.Redeem, stringResource(R.string.birthday), user.birthday)
        Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))

        InfoRowForUser(Icons.Filled.LocationOn, stringResource(R.string.location), user.country)
        Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))

        //NotificationsOptions(user.notificationTemplates)
        Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))

        Spacer(Modifier.weight(1f))

        if (userId) {
            FriendshipActionButton(
                status = friendshipStatus,
                onAccept = onAccept,
                onReject = onReject,
                onAction = onAction,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
        } else {
            Button(
                onClick = { navController.navigate(Routes.EditUserProfile.route) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = CustomTheme.colors.content,
                    containerColor = light_container
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.edit_profile),
                    style = AppTypography.bodyMedium,
                    color = dark_content,
                    modifier = Modifier.padding(end = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun FriendshipActionButton(
    status: FriendshipStatus,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (status) {
        FriendshipStatus.REQUEST_RECEIVED -> {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onAccept,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.colors.currentContainer,
                        contentColor = CustomTheme.colors.currentContent
                    ),
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.accept_request),
                        style = AppTypography.bodyMedium
                    )
                }

                Button(
                    onClick = onReject,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red.copy(alpha = 0.2f),
                        contentColor = Color.Red
                    ),
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.reject),
                        style = AppTypography.bodyMedium
                    )
                }
            }
        }
        else -> {
            val (text, colors) = when (status) {
                FriendshipStatus.NOT_FRIENDS -> Pair(
                    stringResource(R.string.add_friend),
                    ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.colors.currentContainer,
                        contentColor = CustomTheme.colors.currentContent
                    )
                )
                FriendshipStatus.REQUEST_SENT -> Pair(
                    stringResource(R.string.request_sent),
                    ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.colors.container_2,
                        contentColor = CustomTheme.colors.content
                    )
                )
                FriendshipStatus.FRIENDS -> Pair(
                    stringResource(R.string.remove_friend),
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Red.copy(alpha = 0.2f),
                        contentColor = Color.Red
                    )
                )
                else -> Pair("", ButtonDefaults.buttonColors())
            }

            Button(
                onClick = onAction,
                shape = RoundedCornerShape(8.dp),
                colors = colors,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(
                    text = text,
                    style = AppTypography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun NotificationsOptions(notificationTemplates: List<SignupNotificationOption>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        InfoRowForUser(Icons.Filled.Notifications, stringResource(R.string.options_for_receiving_notifications), "")
        LazyRow(
            modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            items(notificationTemplates) { item ->
                Text(
                    text = item.notificationOption,
                    style = AppTypography.bodySmall,
                    color = CustomTheme.colors.content,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(light_container, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun InfoRowForUser(icon: ImageVector, firstText: String, secondText: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "icons",
            modifier = Modifier.size(24.dp).padding(end = 8.dp)
        )
        Text(
            text = "$firstText:",
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content,
            modifier = Modifier.padding(end = 8.dp),
        )
        Text(
            text = secondText,
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun FriendsAndSubscription(user: User, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ItemFriendsOrSubscription(
            title = stringResource(R.string.friends),
            text = user.friends.size.toString(),
            modifier = Modifier.weight(1f)
        )
        ItemFriendsOrSubscription(
            title = stringResource(R.string.subscriptions),
            text = user.subscriptions.size.toString(),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ItemFriendsOrSubscription(
    title: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = CustomTheme.colors.container_2,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = AppTypography.titleMedium,
                color = CustomTheme.colors.content
            )

            Spacer(
                Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(CustomTheme.colors.content)
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    style = AppTypography.titleMedium,
                    color = CustomTheme.colors.content,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun RowButton(image: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = image,
            contentDescription = "icons",
            modifier = Modifier.size(24.dp).padding(end = 8.dp)
        )
        Text(
            text = text,
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content,
            modifier = Modifier.padding(end = 8.dp),
        )
    }
}

@Composable
private fun SettingAndLogOut(modifier: Modifier = Modifier, settingClick: () -> Unit, logOutClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RowButton(
            image = Icons.Filled.Settings,
            text = stringResource(R.string.settings),
            modifier = Modifier.clickable { settingClick() }
        )
        RowButton(
            image = Icons.AutoMirrored.Filled.Logout,
            text = stringResource(R.string.log_out),
            modifier = Modifier.clickable { logOutClick() }
        )
    }

}