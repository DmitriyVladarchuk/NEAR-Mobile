package com.example.near.ui.screens.community.profile

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
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.near.R
import com.example.near.domain.community.models.Community
import com.example.near.domain.shared.models.EmergencyType
import com.example.near.ui.components.common.ItemEmergencyType
import com.example.near.ui.components.headers.SecondaryHeaderTextInfo
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.current_container
import com.example.near.ui.theme.dark_content
import com.example.near.ui.theme.light_container


@Composable
fun ProfileCommunityScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProfileCommunityViewModel = hiltViewModel()
) {
    var showLogout by remember { mutableStateOf(false) }
    var scrollOffset by remember { mutableFloatStateOf(0f) }
    var maxScrollOffset by remember { mutableFloatStateOf(0f) }
    val scrollThreshold = 100.dp

    LaunchedEffect(Unit) {
        viewModel.loadCommunity()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    scrollOffset += dragAmount
                    maxScrollOffset = maxOf(maxScrollOffset, scrollOffset)
                    if (dragAmount < 0) {
                        showLogout = true
                    }
                    else if (scrollOffset > maxScrollOffset - scrollThreshold.toPx()) {
                        showLogout = false
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Box(modifier = Modifier.fillMaxWidth().weight(0.3f)) {
                AvatarSection(
                    avatarUrl = viewModel.avatarUrl,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().weight(0.15f)) {
                SubscribersSection(
                    text = viewModel.community?.subscribers?.size.toString(),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().weight(0.55f)) {
                DescriptionCommunitySection(
                    community = viewModel.community,
                    subscriptionStatus = viewModel.subscriptionStatus,
                    emergencyTypes = viewModel.emergencyTypes,
                    modifier = Modifier.fillMaxSize(),
                    onSubscriptionClick = {  },
                    onEditClick = { navController.navigate(Routes.EditCommunityProfile.route) }
                )
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
                logOutClick = { viewModel.logOut {
                    navController.navigate(Routes.Onboarding.route) {
                        popUpTo(0) { inclusive = true }
                    }
                } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
            )
        }
    }
}

@Composable
fun ProfileCommunityScreen(
    communityId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProfileCommunityViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.loadCommunity(communityId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SecondaryHeaderTextInfo(
            text = stringResource(R.string.community),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            navController.popBackStack()
        }

        Column(
            modifier = Modifier
                .weight(1f)
        ) {

            Box(modifier = Modifier.fillMaxWidth().weight(0.3f)) {
                AvatarSection(
                    avatarUrl = viewModel.avatarUrl,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().weight(0.1f)) {
                SubscribersSection(
                    text = viewModel.community?.subscribers?.size.toString(),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth().weight(0.6f)) {
                DescriptionCommunitySection(
                    community = viewModel.community,
                    subscriptionStatus = viewModel.subscriptionStatus,
                    communityId = true,
                    modifier = Modifier.fillMaxSize(),
                    onSubscriptionClick = { viewModel.handleSubscribe(communityId) },
                    onEditClick = { navController.navigate(Routes.EditCommunityProfile.route) }
                )
            }
            Spacer(modifier.height(8.dp))
        }
    }
}

@Composable
private fun AvatarSection(avatarUrl: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "community avatar",
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .aspectRatio(1f),
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(R.drawable.near_community_small),
            error = painterResource(R.drawable.near_community_small)
        )
    }
}

@Composable
private fun SubscribersSection(text: String, modifier: Modifier = Modifier) {
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
                text = stringResource(R.string.subscribers),
                style = AppTypography.titleMedium,
                color = CustomTheme.colors.content
            )

            Spacer(
                Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
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
                    style = AppTypography.titleLarge,
                    color = CustomTheme.colors.content,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun DescriptionCommunitySection(
    community: Community?,
    subscriptionStatus: SubscriptionStatus,
    emergencyTypes: List<EmergencyType> = emptyList(),
    modifier: Modifier = Modifier,
    communityId: Boolean = false,
    onSubscriptionClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = CustomTheme.colors.container_2, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
        community?.let {
            Text(
                text = community.communityName,
                style = AppTypography.titleMedium,
                color = CustomTheme.colors.content,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(2.dp).background(CustomTheme.colors.content))

            InfoRow(Icons.Default.Info, "Id", community.id)
            Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))

            community.description?.let {
                InfoRow(Icons.Default.Description, stringResource(R.string.description), community.description.toString())
                Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))
            }

            InfoRow(Icons.Default.LocationOn, stringResource(R.string.emergency_monitoring_region), community.country.toString())
            Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))

            InfoRow(Icons.Default.Notifications, stringResource(R.string.type_of_monitored_emergency), "")

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(emergencyTypes) { type ->
                    ItemEmergencyType(
                        emergencyType = type,
                        modifier = Modifier
                    )
                }
            }
            Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))

            Spacer(Modifier.weight(1f))
            Button(
                onClick = {
                    if (communityId) onSubscriptionClick()
                    else onEditClick()
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(contentColor = CustomTheme.colors.content, containerColor = light_container),
                modifier = Modifier
                    .align(if (communityId) Alignment.CenterHorizontally else Alignment.End)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Text(
                    text = if (communityId) {
                        if (subscriptionStatus == SubscriptionStatus.NOT_SUBSCRIBE) stringResource(R.string.subscribe)
                        else stringResource(R.string.unsubscribe)
                    } else stringResource(R.string.edit_profile),
                    style = AppTypography.bodyMedium,
                    color = dark_content,
                    modifier = Modifier.padding(end = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, firstText: String, secondText: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "icons",
            modifier = Modifier.size(24.dp).padding(end = 8.dp),
            tint = current_container
        )
        Text(
            text = "$firstText:",
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content,
            modifier = Modifier.padding(end = 8.dp),
        )
        SelectionContainer {
            Text(
                text = secondText,
                style = AppTypography.bodyMedium,
                color = CustomTheme.colors.content,
                fontWeight = FontWeight.Bold
            )
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
