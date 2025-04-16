package com.example.near.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.near.R
import com.example.near.domain.models.NotificationOption
import com.example.near.domain.models.User
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme
import com.example.near.ui.theme.dark_content

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel(), modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .background(CustomTheme.colors.background)
        ) {
            // Основной контент (ровно 100% видимой области)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Аватар (30% высоты экрана)
                    UserAvatarSection(
                        avatarUrl = viewModel.avatarUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f)
                    )

                    // Профиль (50% высоты экрана)
                    UserProfileCard(
                        user = viewModel.user,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .weight(0.4f)
                    )

                    // Друзья и подписки (20% высоты экрана)
                    FriendsAndSubscription(
                        user = viewModel.user,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .weight(0.1f)
                    )
                }
            }

            // Дополнительный контент (кнопки, появляются при скролле)
            SettingAndLogOut(
                settingClick = {},
                logOutClick = {},
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
                .fillMaxHeight(0.8f) ,
                //.aspectRatio(1f), // Сохраняем квадратную форму
            contentScale = ContentScale.Crop,
            //placeholder = painterResource(R.drawable.default_avatar),
            //error = painterResource(R.drawable.default_avatar)
        )
    }
}

@Composable
private fun UserProfileCard(user: User, modifier: Modifier = Modifier) {
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

        NotificationsOptions(user.notificationTemplates)
        Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))

        Spacer(Modifier.weight(1f))
        Button(
            onClick = {},
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(contentColor = CustomTheme.colors.content, containerColor = CustomTheme.colors.container),
            modifier = Modifier.align(Alignment.End).padding(horizontal = 8.dp, vertical = 8.dp)
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

@Composable
private fun NotificationsOptions(notificationTemplates: List<NotificationOption>, modifier: Modifier = Modifier) {
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
                    text = item.type,
                    style = AppTypography.bodySmall,
                    color = CustomTheme.colors.content,
                    modifier = Modifier
                        .padding(8.dp)
                        .background(CustomTheme.colors.container, RoundedCornerShape(8.dp))
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
    Column(
        modifier = modifier
            .background(color = CustomTheme.colors.container_2, shape = RoundedCornerShape(8.dp))
    ) {
        Text(
            text = title,
            style = AppTypography.titleMedium,
            color = CustomTheme.colors.content,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Spacer(
            Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(CustomTheme.colors.content)
        )
        Text(
            text = text,
            style = AppTypography.titleMedium,
            color = CustomTheme.colors.content,
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .weight(1f),
        )
    }
}

@Composable
private fun SettingAndLogOut(settingClick: () -> Unit, logOutClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfoRowForUser(Icons.Filled.Settings, stringResource(R.string.settings), "")
        InfoRowForUser(Icons.AutoMirrored.Filled.Logout, stringResource(R.string.log_out), "")
    }

}


@Preview(showBackground = true)
@Composable
private fun TestProfileScreen() {
    NEARTheme {
        ProfileScreen()
    }
}