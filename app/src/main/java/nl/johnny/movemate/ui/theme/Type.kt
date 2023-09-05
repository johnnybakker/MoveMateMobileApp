package nl.johnny.movemate.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import nl.johnny.movemate.R

val PromptFont = FontFamily(
    Font(R.font.prompt_regular, FontWeight.Normal),
    Font(R.font.prompt_bold,FontWeight.Bold),
    Font(R.font.prompt_light, FontWeight.Light),
    Font(R.font.prompt_semi_bold, FontWeight.SemiBold),
    Font(R.font.prompt_extra_bold, FontWeight.ExtraBold),
    Font(R.font.prompt_extra_light, FontWeight.ExtraLight),
    Font(R.font.prompt_thin, FontWeight.Thin),
    Font(R.font.prompt_medium, FontWeight.Medium)
)

// Set of Material typography styles to start with
fun Typography(scheme: ColorScheme) = Typography(
    bodyLarge = TextStyle(
        fontFamily = PromptFont,
        fontWeight = FontWeight.Medium,
        color = scheme.tertiary,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)