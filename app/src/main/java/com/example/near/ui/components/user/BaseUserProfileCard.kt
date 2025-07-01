package com.example.near.ui.components.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.R
import com.example.near.domain.shared.models.NotificationOption
import com.example.near.domain.shared.models.SignupNotificationOption
import com.example.near.domain.user.models.User
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme
import com.example.near.ui.theme.light_container

@Composable
fun BaseUserProfileCard(
    modifier: Modifier = Modifier,
    user: User?,
    notificationOption: List<NotificationOption> = emptyList(),
    content: @Composable ColumnScope.() -> Unit
) {
    user?.let {
        Column(
            modifier = modifier
                .background(
                    color = CustomTheme.colors.container_2,
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
        ) {
            Text(
                text = "${user.firstName} ${user.lastName}",
                style = AppTypography.titleMedium,
                color = CustomTheme.colors.content,
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
            Divider()

            UserInfoRow(
                icon = Icons.Filled.Redeem,
                label = stringResource(R.string.birthday),
                value = user.birthday,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
            Divider()

            UserInfoRow(
                icon = Icons.Filled.LocationOn,
                label = stringResource(R.string.location),
                value = user.country,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            )
            Divider()

            NotificationsOptions(
                notificationOption = notificationOption,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Divider()

            Spacer(Modifier.weight(1f))

            content()
        }
    }
}

@Composable
private fun NotificationsOptions(notificationOption: List<NotificationOption>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        UserInfoRow(Icons.Filled.Notifications, stringResource(R.string.options_for_receiving_notifications), "")
        LazyRow(
            modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            items(notificationOption) { item ->
                Text(
                    text = item.title,
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
private fun Divider() {
    Spacer(
        Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(CustomTheme.colors.content)
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, fontScale = 1.5f, name = "Large Font")
@Composable
fun BaseUserProfileCardPreview() {
    val mockUser = User(
        id = "user123",
        firstName = "Иван",
        lastName = "Иванов",
        birthday = "15.05.1990",
        age = 35,
        country = "Россия",
        city = "Москва",
        district = "ЦАО",
        registrationDate = "10.01.2020",
        friends = emptyList(),
        groups = emptyList(),
        subscriptions = emptyList(),
        notificationTemplates = emptyList()
    )

    NEARTheme {
        BaseUserProfileCard(
            user = mockUser,
            content = {}
        )
    }
}