package com.example.near.ui.screens.friendsAndGroups.groups

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.near.R
import com.example.near.ui.views.SecondaryHeaderTextInfo

@Composable
fun CreateGroupsScreen(navController: NavController, viewModel: GroupsViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        SecondaryHeaderTextInfo(text = stringResource(R.string.create_new_groups)) {
            navController.popBackStack()
        }
    }
}