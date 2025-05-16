package com.example.near.ui.screens.profile.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.near.R
import com.example.near.data.models.community.CommunityResponse
import com.example.near.ui.screens.navigation.Routes
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.current_container
import com.example.near.ui.theme.dark_content
import com.example.near.ui.theme.light_container
import com.example.near.ui.views.SecondaryHeaderTextInfo

@Composable
fun ProfileCommunityScreen(
    communityId: String? = null,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ProfileCommunityViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    LaunchedEffect(Unit) {
        if (communityId == null) {
            viewModel.loadCommunity()
        } else {
            viewModel.loadCommunity(communityId)
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        if (communityId != null) {
            SecondaryHeaderTextInfo(
                text = stringResource(R.string.community),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                navController.popBackStack()
            }
        }

        when {
            viewModel.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            viewModel.error != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error: ${viewModel.error}", color = Color.Red)
                    Button(onClick = { viewModel.loadCommunity() }) {
                        Text("Retry")
                    }
                }
            }
            else -> {

                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(screenHeight)
                    ) {
                        AvatarSection(
                            avatarUrl = viewModel.avatarUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((screenHeight - 56.dp) * 0.3f)
                                .padding(8.dp)
                        )

                        viewModel.community?.let { it ->
                            DescriptionCommunitySection(
                                community = it,
                                communityId = communityId != null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(vertical = 8.dp)
                            ) {
                                viewModel.handleSubscribe(it.id)
                            }
                        }

                        viewModel.community?.let { it ->
                            SubscribersSection(
                                text = it.subscribers.size.toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height((screenHeight - 56.dp) * 0.15f)
                            )
                        }


                        if (communityId == null)
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
                .aspectRatio(1f), // Сохраняем квадратную форму
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
private fun DescriptionCommunitySection(
    community: CommunityResponse,
    modifier: Modifier = Modifier,
    communityId: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(color = CustomTheme.colors.container_2, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
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

//        InfoRow(Icons.Default.Description, stringResource(R.string.description), community.description.toString())
//        Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))

        InfoRow(Icons.Default.LocationOn, stringResource(R.string.emergency_monitoring_region), community.country.toString())
        Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))

        InfoRow(Icons.Default.Notifications, stringResource(R.string.type_of_monitored_emergency), "")
        Spacer(Modifier.padding(horizontal = 8.dp).fillMaxWidth().height(1.dp).background(CustomTheme.colors.content))

        Spacer(Modifier.weight(1f))
        Button(
            onClick = { onClick() },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(contentColor = CustomTheme.colors.content, containerColor = light_container),
            modifier = Modifier
                .align(if (communityId) Alignment.CenterHorizontally else Alignment.End)
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                text = if (communityId) stringResource(R.string.subscribe) else stringResource(R.string.edit_profile),
                style = AppTypography.bodyMedium,
                color = dark_content,
                modifier = Modifier.padding(end = 8.dp),
            )
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
