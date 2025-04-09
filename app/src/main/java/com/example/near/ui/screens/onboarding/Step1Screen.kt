package com.example.near.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.near.R
import com.example.near.ui.theme.AppTypography
import com.example.near.ui.theme.CustomTheme
import com.example.near.ui.theme.NEARTheme

@Composable
fun Step1Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomTheme.colors.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Изображение с текстовыми элементами (занимает 60% экрана)
        Box(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
        ) {
            ImageNear()
        }

        // Текстовый блок (занимает 40% экрана)
        Column(
            modifier = Modifier
                .weight(0.3f)
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            InformationText()
        }
    }
}

@Composable
private fun ImageNear() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.8f)
                .graphicsLayer(clip = false),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.unsplash1),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
            )

            TextForImage(
                text = stringResource(R.string.respond),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = 10.dp)
            )

            TextForImage(
                text = stringResource(R.string.notify),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = (-20).dp)
            )

            TextForImage(
                text = stringResource(R.string.alarm),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 20.dp, y = (-10).dp)
            )
        }
    }
}

@Composable
private fun TextForImage(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                clip = true
            )
            .background(
                color = CustomTheme.colors.background,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        val firstChar = text.take(1)
        val remainingChars = text.drop(1)

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = CustomTheme.colors.orange)) {
                    append(firstChar)
                }
                append(remainingChars)
            },
            style = AppTypography.bodyMedium,
            fontSize = 24.sp,
            color = CustomTheme.colors.content
        )
    }
}

@Composable
private fun InformationText() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.title_text_onboarding),
            style = AppTypography.headlineMedium,
            color = CustomTheme.colors.content
        )
        Text(
            text = stringResource(R.string.text_onboarding1),
            style = AppTypography.bodyMedium,
            color = CustomTheme.colors.content
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TestStep1Screen() {
    NEARTheme {
        Step1Screen()
    }
}