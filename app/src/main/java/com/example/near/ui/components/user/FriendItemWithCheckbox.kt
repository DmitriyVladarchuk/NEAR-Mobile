package com.example.near.ui.components.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.near.R
import com.example.near.feature.user.domain.models.UserFriend
import com.example.near.core.ui.theme.AppTypography
import com.example.near.core.ui.theme.CustomTheme
import com.example.near.core.ui.theme.NEARTheme

@Composable
fun FriendItemWithCheckbox(
    friend: UserFriend,
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelectionChange(!isSelected) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = onSelectionChange,
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

@Preview(showBackground = true)
@Composable
private fun TestFriendItemWithCheckbox() {
    NEARTheme {
        FriendItemWithCheckbox(
            friend = UserFriend(
                "1",
                "Aboba",
                "Boba",
                "",
                5,
                "",
                "",
                "",
                "",
                10,
                10
            ),
            isSelected = false,
            onSelectionChange = {}
        )
    }
}