package com.example.near.ui.screens.templates

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.near.R
import com.example.near.domain.user.models.User
import com.example.near.domain.user.models.UserGroup
import com.example.near.domain.user.models.UserTemplate
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.dark_content
import com.example.near.ui.components.decorations.DynamicItemContainer
import com.example.near.ui.components.headers.SecondaryHeaderTextInfo

@Composable
fun InfoTemplateScreen(
    templateId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: InfoTemplateViewModel = hiltViewModel()
) {
    val tabs = listOf(stringResource(R.string.friends), stringResource(R.string.groups))
    var selectedTab by remember { mutableStateOf(tabs[0]) }
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    LaunchedEffect(templateId) {
        viewModel.loadData(templateId)
    }

    LaunchedEffect(selectedTab) {
        val page = tabs.indexOf(selectedTab)
        if (page != pagerState.currentPage) {
            pagerState.animateScrollToPage(page)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SecondaryHeaderTextInfo(
            text = stringResource(R.string.template_info),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            navController.popBackStack()
        }

        TemplateInfo(viewModel.template)

        DynamicItemContainer(
            items = tabs,
            selectedItem = selectedTab,
            onItemSelected = { tab -> selectedTab = tab },
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        ) { item, isSelected, modifier, onClick ->
            TabItem(
                text = item,
                isSelected = isSelected,
                modifier = modifier,
                onClick = { onClick(item) }
            )
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = true,
        ) { page ->
            when (page) {
                0 -> FriendsListScreen(
                    friends = viewModel.friends?.friends ?: emptyList(),
                    selectedRecipients = viewModel.recipients,
                    onRecipientToggle = { friendId ->
                        viewModel.toggleRecipient(friendId)
                    }
                )
                1 -> GroupsListScreen(
                    groups = viewModel.groups ?: emptyList(),
                    selectedRecipients = viewModel.recipients,
                    onRecipientToggle = { members ->
                        viewModel.toggleGroupRecipients(members)
                    }
                )
            }
        }

        AnimatedVisibility(viewModel.recipients.isNotEmpty()) {
            Button(
                onClick = {
                    viewModel.saveRecipients()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
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
private fun TemplateInfo(template: UserTemplate?) {
    template?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(template.emergencyType.color.toColorInt()),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
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
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = template.message,
                style = AppTypography.bodyMedium,
                color = dark_content
            )
        }
    }
}

@Composable
private fun FriendsListScreen(
    friends: List<User>,
    selectedRecipients: List<String>,
    onRecipientToggle: (String) -> Unit
) {
    LazyColumn {
        items(friends) { friend ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRecipientToggle(friend.id) }
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedRecipients.contains(friend.id),
                    onCheckedChange = { onRecipientToggle(friend.id) },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = CustomTheme.colors.orange,
                        uncheckedColor = CustomTheme.colors.content
                    )
                )
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

                Text(
                    text = "${friend.firstName} ${friend.lastName}",
                    style = AppTypography.bodyMedium,
                    color = CustomTheme.colors.content,
                )
            }
        }
    }
}

@Composable
private fun GroupsListScreen(
    groups: List<UserGroup>,
    selectedRecipients: List<String>,
    onRecipientToggle: (List<String>) -> Unit
) {
    LazyColumn {
        items(groups) { group ->
            val allMembersSelected = group.members.all { member ->
                selectedRecipients.contains(member.id)
            }

            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onRecipientToggle(
                                group.members.map { it.id }
                            )
                        }
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = allMembersSelected,
                        onCheckedChange = {
                            onRecipientToggle(
                                group.members.map { it.id }
                            )
                        },
                        modifier = Modifier.padding(end = 8.dp),
                        colors = CheckboxDefaults.colors(
                            checkedColor = CustomTheme.colors.orange,
                            uncheckedColor = CustomTheme.colors.content
                        )
                    )

                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = "Group",
                        modifier = Modifier
                            .size(48.dp)
                            .aspectRatio(1f)
                            .padding(end = 8.dp),
                    )
                    Column {
                        Text(
                            text = group.groupName,
                            style = AppTypography.bodyMedium,
                            color = CustomTheme.colors.content,
                        )
                        Text(
                            text = "${group.members.size} ${stringResource(R.string.members)}",
                            style = AppTypography.bodyMedium,
                            color = CustomTheme.colors.content.copy(alpha = 0.7f),
                        )
                    }
                }

                // Показываем членов группы, если группа выбрана
                AnimatedVisibility(allMembersSelected) {
                    Column {
                        group.members.forEach { member ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 72.dp, end = 16.dp, bottom = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = "", // URL аватара
                                    contentDescription = "Avatar",
                                    modifier = Modifier.size(32.dp),
                                    placeholder = painterResource(R.drawable.default_avatar),
                                    error = painterResource(R.drawable.default_avatar)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "${member.firstName} ${member.lastName}",
                                    style = AppTypography.bodyMedium,
                                    color = CustomTheme.colors.content,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabItem(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = if (isSelected) AppTypography.titleMedium else AppTypography.bodyMedium,
            color = if (isSelected) CustomTheme.colors.content else CustomTheme.colors.content.copy(alpha = 0.6f)
        )
    }
}
