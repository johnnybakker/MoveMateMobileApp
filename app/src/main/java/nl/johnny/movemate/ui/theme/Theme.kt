package nl.johnny.movemate.ui.theme

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import kotlinx.coroutines.delay
import nl.johnny.movemate.R
import nl.johnny.movemate.ui.components.Alert
import nl.johnny.movemate.ui.components.Menu
import nl.johnny.movemate.ui.components.MenuItem
import nl.johnny.movemate.ui.components.TopBar
import nl.johnny.movemate.ui.theme.MoveMateTheme.alertColor
import nl.johnny.movemate.ui.theme.MoveMateTheme.alertText
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private val DarkColorScheme = darkColorScheme(
    primary = Blue,
    secondary = DarkGray,
    tertiary = White,
    background = Dark,
    surface = Dark,
    inverseSurface = Light
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    secondary = DarkGray,
    tertiary = Dark,
    background = White,
    surface = White,
    inverseSurface = DarkGray
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
                val icon = painterResource(id = R.mipmap.ic_launcher_round_adaptive_fore)

                TopBar(title)

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

            AnimatedVisibility(
                visible = alertText.isNotEmpty(),
                enter = slideInVertically { -it },
                exit = slideOutVertically { -it },
            ) {

                Alert(text = alertText, color = alertColor)

                LaunchedEffect(Unit) {
                    closeAlert(2.seconds)
                }

            }
        }
    )
}

private suspend fun closeAlert(after: Duration) {
    delay(after)
    alertText = ""
}

object MoveMateTheme {

    private var alertTextState: MutableState<String> = mutableStateOf("")
    private var alertColorState: MutableState<Color> = mutableStateOf(Danger)

    var alertText: String
        get() = alertTextState.value
        set(value) { alertTextState.value = value }

    var alertColor: Color
        get() = alertColorState.value
        set(value) { alertColorState.value = value }

    fun alert(text: String, color: Color) {
        alertTextState.value = text
        alertColorState.value = color
    }

    fun alertError(text: String) {
        alertTextState.value = text
        alertColorState.value = Danger
    }

    fun alertWarning(text: String) {
        alertTextState.value = text
        alertColorState.value = Warning
    }

    fun alertSuccess(text: String) {
        alertTextState.value = text
        alertColorState.value = Success
    }
}

@Composable
@Preview(name = "Theme preview", uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED, showBackground = true)
fun MoveMateThemePreview() {
    MoveMateTheme {
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth())
    }
}