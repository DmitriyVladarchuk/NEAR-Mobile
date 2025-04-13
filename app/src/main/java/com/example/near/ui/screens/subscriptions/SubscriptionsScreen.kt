package com.example.near.ui.screens.subscriptions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.near.R
import com.example.near.ui.views.MainHeaderTextInfo

@Composable
fun SubscriptionsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        MainHeaderTextInfo(
            text = stringResource(R.string.subscriptions),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}