package com.example.near.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.near.core.ui.theme.Dimens
import com.example.near.core.ui.theme.NEARTheme

/**
 * Размеры для иконок/аватарок
 *
 * @param EXTRA_SMALL 16dp
 * @param SMALL 24dp
 * @param MEDIUM 32dp
 * @param LARGE 48dp
 * @param EXTRA_LARGE 64dp
 */
enum class IconSize {
    EXTRA_SMALL,
    SMALL,
    MEDIUM,
    LARGE,
    EXTRA_LARGE
}

/**
 * Конвертирует enum размер в конкретное значение Dp
 */
private fun IconSize.toDp() = when (this) {
    IconSize.EXTRA_SMALL -> Dimens.iconSizeExtraSmall
    IconSize.SMALL -> Dimens.iconSizeSmall
    IconSize.MEDIUM -> Dimens.iconSizeMedium
    IconSize.LARGE -> Dimens.iconSizeLarge
    IconSize.EXTRA_LARGE -> Dimens.iconSizeExtraLarge
}

/**
 * Универсальный компонент для отображения аватарок.
 * Поддерживает ImageVector и URL через Coil.
 * Всегда круглой формы.
 *
 * @param modifier Модификатор для дополнительной настройки
 * @param size Размер иконки [IconSize]
 * @param contentDescription Описание для иконки
 */
@Composable
fun AppIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    size: IconSize = IconSize.MEDIUM,
    contentDescription: String? = null,
) {
    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier
            .size(size.toDp())
            .clip(CircleShape),
    )
}

/**
 * Вариант компонента AppIcon для загрузки изображений по URL через Coil.
 * Используется для отображения аватарок пользователей и других изображений из сети.
 *
 * @param imageUrl URL изображения для загрузки.
 * @param modifier Модификатор для дополнительной настройки.
 * @param size Размер иконки. По умолчанию [IconSize.MEDIUM] (32dp).
 * @param contentDescription Описание
 * @param placeholder Painter для отображения во время загрузки изображения.
 * @param error Painter для отображения при ошибке загрузки изображения.
 */
@Composable
fun AppIcon(
    imageUrl: String,
    modifier: Modifier = Modifier,
    size: IconSize = IconSize.MEDIUM,
    contentDescription: String? = null,
    placeholder: Painter? = null,
    error: Painter? = null
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier
            .size(size.toDp())
            .clip(CircleShape),
        contentScale = ContentScale.Crop,
        placeholder = placeholder,
        error = error
    )
}

@Preview(showBackground = true)
@Composable
private fun AppIconPreview() {
    NEARTheme {
        Column {
            Row {
                AppIcon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Person,
                    size = IconSize.EXTRA_SMALL,
                    contentDescription = "Small icon"
                )
                AppIcon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Person,
                    size = IconSize.SMALL,
                    contentDescription = "Medium icon"
                )
                AppIcon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Person,
                    size = IconSize.MEDIUM,
                    contentDescription = "Large icon"
                )
            }
            Row {
                AppIcon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Person,
                    size = IconSize.LARGE,
                    contentDescription = "Medium icon"
                )
                AppIcon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Person,
                    size = IconSize.EXTRA_LARGE,
                    contentDescription = "Large icon"
                )
            }
        }
    }
}
