package com.example.near.ui.components.decorations

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("SuspiciousModifierThen")
fun Modifier.dashedBorder(
    color: Color,
    cornerRadius: Dp = 8.dp,
    strokeWidth: Dp = 1.dp,
    dashWidth: Dp = 5.dp,
    gapWidth: Dp = 3.dp
) = this.then(
    drawWithCache {
        val strokeWidthPx = strokeWidth.toPx()
        val dashWidthPx = dashWidth.toPx()
        val gapWidthPx = gapWidth.toPx()

        onDrawWithContent {
            drawContent()
            drawRoundRect(
                color = color,
                style = Stroke(
                    width = strokeWidthPx,
                    pathEffect = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(dashWidthPx, gapWidthPx),
                        phase = 0f
                    )
                ),
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }
    }
)