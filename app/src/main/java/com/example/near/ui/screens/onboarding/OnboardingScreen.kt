package com.example.near.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.near.ui.theme.CustomTheme
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onAccountClick: () -> Unit = {},
    onCommunityClick: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> Step1Screen()
                1 -> Step2Screen()
                2 -> Step3Screen(onAccountClick = onAccountClick, onCommunityClick = onCommunityClick)
            }
        }

        // Нижняя панель с индикаторами и кнопками
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Индикаторы (точки)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(
                                color = if (pagerState.currentPage == index)
                                    CustomTheme.colors.orange
                                else
                                    CustomTheme.colors.background.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = if (pagerState.currentPage == index)
                                    Color.Transparent
                                else
                                    CustomTheme.colors.orange,
                                shape = CircleShape
                            )
                    )
                }
            }

            // Кнопки навигации
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Кнопка "Назад"
                if (pagerState.currentPage > 0) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = CustomTheme.colors.background,
                                shape = CircleShape
                            )
                            .border(
                                width = 1.dp,
                                color = CustomTheme.colors.orange,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = CustomTheme.colors.orange,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Кнопка "Далее" или "Готово"
                if (pagerState.currentPage < 2) {
                    IconButton(
                        onClick = {
                            if (pagerState.currentPage < 2) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = CustomTheme.colors.orange,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}