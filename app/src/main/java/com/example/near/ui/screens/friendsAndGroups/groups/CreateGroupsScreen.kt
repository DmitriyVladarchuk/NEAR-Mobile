package com.example.near.ui.screens.friendsAndGroups.groups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.views.FriendItemWithCheckbox
import com.example.near.ui.views.SecondaryHeaderTextInfo
import com.example.near.ui.views.TextFieldLabel
import com.example.near.ui.views.TextFieldPlaceholder
import com.example.near.ui.views.textFieldColors

@Composable
fun CreateGroupsScreen(
    navController: NavController,
    viewModel: GroupsViewModel = hiltViewModel(),
    groupId: String? = null
) {
    val editingGroup by remember(groupId) {
        derivedStateOf {
            groupId?.let { id -> viewModel.groups.find { it.id == id } }
        }
    }

    var nameGroup by remember { mutableStateOf("") }
    var selectedFriends by remember { mutableStateOf<Set<String>>(emptySet()) }

    LaunchedEffect(groupId, viewModel.groups) {
        editingGroup?.let { group ->
            nameGroup = group.groupName
            selectedFriends = group.members.map { it.id }.toSet()
        } ?: run {
            nameGroup = ""
            selectedFriends = emptySet()
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        SecondaryHeaderTextInfo(
            text = stringResource(
                if (editingGroup != null) R.string.edit_group else R.string.create_new_groups
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            navController.popBackStack()
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            value = nameGroup,
            onValueChange = { nameGroup = it },
            singleLine = true,
            label = { TextFieldLabel(stringResource(R.string.group_name)) },
            placeholder = { TextFieldPlaceholder(stringResource(R.string.group_name)) },
            textStyle = AppTypography.bodyMedium,
            colors = textFieldColors()
        )

        Text(
            text = stringResource(R.string.select_friends),
            style = AppTypography.titleMedium,
            color = CustomTheme.colors.content,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            viewModel.friends.forEach { friend ->
                FriendItemWithCheckbox(
                    friend = friend,
                    isSelected = selectedFriends.contains(friend.id),
                    onSelectionChange = { selected ->
                        selectedFriends = if (selected) {
                            selectedFriends + friend.id
                        } else {
                            selectedFriends - friend.id
                        }
                    }
                )
            }
        }

        if (editingGroup != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (nameGroup.isNotBlank() && selectedFriends.isNotEmpty()) {
                            viewModel.updateGroup(
                                editingGroup!!.id,
                                nameGroup,
                                selectedFriends.toList()
                            )
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = nameGroup.isNotBlank() && selectedFriends.isNotEmpty(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.colors.orange,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(R.string.save_changes))
                }

                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CustomTheme.colors.container_2,
                        contentColor = CustomTheme.colors.content
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        } else {
            Button(
                onClick = {
                    if (nameGroup.isNotBlank() && selectedFriends.isNotEmpty()) {
                        viewModel.createGroup(nameGroup, selectedFriends.toList())
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                enabled = nameGroup.isNotBlank() && selectedFriends.isNotEmpty(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomTheme.colors.orange,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.create_group))
            }
        }
    }
}