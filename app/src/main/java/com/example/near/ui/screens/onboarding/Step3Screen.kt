package com.example.near.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.near.R
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme
import com.example.near.ui.theme.current_container
import com.example.near.ui.theme.dark_content

@Composable
fun Step3Screen(
    onAccountClick: () -> Unit = {},
    onCommunityClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Заголовок
        Text(
            text = "Start using",
            style = AppTypography.headlineLarge,
            color = CustomTheme.colors.content,
            modifier = Modifier
                .padding(top = 48.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Две основные кнопки
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Кнопка создания аккаунта
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(current_container)
                    .clickable(onClick = onAccountClick)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.near_user),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    Text(
                        text = stringResource(R.string.create_an_account),
                        style = AppTypography.headlineSmall,
                        color = dark_content,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(current_container),
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Кнопка создания сообщества
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(current_container)
                    .clickable(onClick = onCommunityClick)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(R.drawable.near_community_small),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    Text(
                        text = stringResource(R.string.create_a_community),
                        style = AppTypography.headlineSmall,
                        color = dark_content,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(current_container),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TestStep3Screen() {
    NEARTheme {
        Step3Screen()
    }
}