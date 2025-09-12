package com.example.near.ui.screens.templates.info

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.core.ui.components.AppIcon
import com.example.near.core.ui.components.CheckableItem
import com.example.near.core.ui.components.CustomTabRow
import com.example.near.core.ui.components.IconSize
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.Dimens
import com.example.near.core.ui.theme.dark_content
import com.example.near.core.ui.theme.screenPadding
import com.example.near.feature.template.domain.model.Template
import com.example.near.feature.user.domain.models.User
import com.example.near.feature.user.domain.models.UserGroup
import com.example.near.ui.components.headers.SecondaryHeaderTextInfo

@Composable
fun InfoTemplateScreen(
    templateId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: InfoTemplateViewModel = hiltViewModel()

    InfoTemplateScreen(
        templateId = templateId,
        navController = navController,
        viewModel = viewModel,
        modifier = modifier
    )
}

@Composable
fun InfoTemplateScreen(
    templateId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: InfoTemplateViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(stringResource(R.string.friends), stringResource(R.string.groups))
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    val state by viewModel.state.collectAsState()
    val effect = viewModel.effect.collectAsStateWithLifecycle(initialValue = null)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(InfoTemplateEvent.LoadData(templateId))
    }

    LaunchedEffect(effect.value) {
        effect.value?.let { nonNullEffect ->
            when (nonNullEffect) {
                InfoTemplateEffect.NavigateBack -> navController.popBackStack()
                is InfoTemplateEffect.ShowError -> {
                    val errorMessage = when (nonNullEffect.error) {
                        TemplateError.LoadDataFailed -> context.getString(R.string.error_load_data)
                        TemplateError.SendTemplateFailed -> context.getString(R.string.error_send_template)
                        is TemplateError.NetworkError -> context.getString(R.string.error_network)
                        is TemplateError.UnknownError -> context.getString(R.string.error_unknown)
                    }
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LaunchedEffect(selectedTab) {
        val page = selectedTab
        if (page != pagerState.currentPage) {
            pagerState.animateScrollToPage(page)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .screenPadding()
    ) {
        SecondaryHeaderTextInfo(
            text = stringResource(R.string.template_info),
            modifier = Modifier.padding(bottom = Dimens.spacingMedium)
        ) {
            navController.popBackStack()
        }

        TemplateInfo(state.template)

        CustomTabRow(
            tabs = tabs,
            selectedTabIndex = selectedTab,
            onTabSelected = { index -> selectedTab = index },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.spacingLarge, bottom = Dimens.spacingMedium)
        )

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = true,
        ) { page ->
            when (page) {
                0 -> FriendsSelectionList(
                    friends = state.friends?.friends ?: emptyList(),
                    selectedRecipients = state.recipients,
                    onRecipientToggle = { friendId ->
                        viewModel.onEvent(InfoTemplateEvent.ToggleRecipient(friendId))
                    }
                )
                1 -> GroupsSelectionList(
                    groups = state.groups,
                    selectedRecipients = state.recipients,
                    onRecipientToggle = { members ->
                        viewModel.onEvent(InfoTemplateEvent.ToggleGroupRecipients(members))
                    }
                )
            }
        }

        AnimatedVisibility(state.recipients.isNotEmpty()) {
            Button(
                onClick = {
                    viewModel.onEvent(InfoTemplateEvent.SendTemplate)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.spacingMedium),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomTheme.colors.orange
                )
            ) {
                Text(
                    text = stringResource(R.string.send_template),
                    style = AppTypography.bodyMedium,
                    color = dark_content
                )
            }
        }
    }
}

@Composable
private fun TemplateInfo(template: Template?) {
    template ?: return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(template.emergencyType.color.toColorInt()),
                shape = RoundedCornerShape(Dimens.cornerRadiusLarge)
            )
            .padding(Dimens.spacingMedium)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = Dimens.spacingSmall)
        ) {
            Text(
                text = template.emergencyType.title,
                style = AppTypography.titleMedium,
                color = dark_content
            )
        }

        Text(
            text = template.templateName,
            style = AppTypography.titleLarge,
            color = dark_content,
            modifier = Modifier.padding(bottom = Dimens.spacingExtraSmall)
        )

        Text(
            text = template.message,
            style = AppTypography.bodyMedium,
            color = dark_content
        )
    }
}

@Composable
private fun FriendsSelectionList(
    friends: List<User>,
    selectedRecipients: List<String>,
    onRecipientToggle: (String) -> Unit
) {
    LazyColumn {
        items(friends) { friend ->
            CheckableItem(
                checked = selectedRecipients.contains(friend.id),
                onCheckedChange = { onRecipientToggle(friend.id) },
                modifier = Modifier.fillMaxWidth(),
                leadingContent = {
                    AppIcon(
                        imageUrl = "",
                        size = IconSize.LARGE,
                        contentDescription = "User avatar",
                        placeholder = painterResource(R.drawable.default_avatar),
                        error = painterResource(R.drawable.default_avatar)
                    )
                },
                mainText = "${friend.firstName} ${friend.lastName}"
            )
        }
    }
}

@Composable
private fun GroupsSelectionList(
    groups: List<UserGroup>,
    selectedRecipients: List<String>,
    onRecipientToggle: (List<String>) -> Unit
) {
    LazyColumn {
        items(groups) { group ->
            val allMembersSelected = group.members.all { member ->
                selectedRecipients.contains(member.id)
            }

            CheckableItem(
                checked = allMembersSelected,
                onCheckedChange = {
                    onRecipientToggle(group.members.map { it.id })
                },
                modifier = Modifier.fillMaxWidth(),
                leadingContent = {
                    AppIcon(
                        imageVector = Icons.Default.Group,
                        size = IconSize.LARGE,
                        contentDescription = "Group"
                    )
                },
                mainText = group.groupName,
                secondaryText = "${group.members.size} ${stringResource(R.string.members)}"
            )
        }
    }
}
