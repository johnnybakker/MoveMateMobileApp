package nl.johnny.movemate.ui.theme

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import nl.johnny.movemate.R
import nl.johnny.movemate.ui.components.Menu
import nl.johnny.movemate.ui.components.MenuItem
import nl.johnny.movemate.ui.components.TopBar

private val DarkColorScheme = darkColorScheme(
    primary = Orange,
    secondary = Blue,
    tertiary = White,
    background = Black,
    surface = Color.Transparent
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    secondary = White,
    tertiary = White,
    background = White,
    surface = Color.Transparent
)

@Composable
fun MoveMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    menuItems: List<MenuItem> = listOf(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(colorScheme),
        content = {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxWidth()
                    .background(colorScheme.background)
            ) {
                val title = stringResource(R.string.app_name)
                val icon = painterResource(id = R.mipmap.ic_launcher_square_adaptive_fore)

                TopBar(title, icon)
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .weight(1.0f)
                        .padding(20.dp)
                ) {
                   content()
                }
                if (menuItems.isNotEmpty()) {
                    Menu(menuItems)
                }
            }
        }
    )
}

@Composable
@Preview(name = "Theme preview", uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED, showBackground = true, )
fun MoveMateThemePreview() {
    MoveMateTheme {
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth())
    }
}