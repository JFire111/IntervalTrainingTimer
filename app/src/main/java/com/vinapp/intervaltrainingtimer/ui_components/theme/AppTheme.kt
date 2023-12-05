package com.vinapp.intervaltrainingtimer.ui_components.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val colors = AppColors(
        red = Color(0xFFF44336),
        green = Color(0xFF4CAF50),
        yellow = Color(0xFFFFC107),
        white = Color(0xFFFFFFFF),
        lightGray = Color(0xFF343434),
        mediumGray = Color(0xFF303030),
        darkGray = Color(0xFF212121),
        inactiveGray = Color(0xFFAAAAAA),
        whiteFontColor = Color(0xFFFFFFFF),
        lightGrayFontColor = Color(0xFFC1C1C1),
        grayFontColor = Color(0xFF646464),
        darkGrayFontColor = Color(0xFF212121),
    )
    val typography = AppTypography(
        small = TextStyle(
            fontSize = 10.sp
        ),
        regular = TextStyle(
            fontSize = 16.sp
        ),
        title = TextStyle(
            fontSize = 18.sp
        ),
        medium = TextStyle(
            fontSize = 24.sp
        ),
        large = TextStyle(
            fontSize = 36.sp
        ),
        extraLarge = TextStyle(
            fontSize = 64.sp
        )
    )
    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        content = content
    )
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current
    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current
}

data class AppTypography(
    val small: TextStyle,
    val regular: TextStyle,
    val medium: TextStyle,
    val title: TextStyle,
    val large: TextStyle,
    val extraLarge: TextStyle,
)

data class AppColors(
    val red: Color,
    val green: Color,
    val yellow: Color,
    val white: Color,
    val lightGray: Color,
    val mediumGray: Color,
    val darkGray: Color,
    val inactiveGray: Color,
    val whiteFontColor: Color,
    val lightGrayFontColor: Color,
    val grayFontColor: Color,
    val darkGrayFontColor: Color,
)

private val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        red = Color.Unspecified,
        green = Color.Unspecified,
        yellow = Color.Unspecified,
        white = Color.Unspecified,
        lightGray = Color.Unspecified,
        mediumGray = Color.Unspecified,
        darkGray = Color.Unspecified,
        inactiveGray = Color.Unspecified,
        whiteFontColor = Color.Unspecified,
        lightGrayFontColor = Color.Unspecified,
        grayFontColor = Color.Unspecified,
        darkGrayFontColor = Color.Unspecified,
    )
}

private val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        small = TextStyle.Default,
        regular = TextStyle.Default,
        medium = TextStyle.Default,
        title = TextStyle.Default,
        large = TextStyle.Default,
        extraLarge = TextStyle.Default
    )
}