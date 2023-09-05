package nl.johnny.movemate.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class ButtonStyle {
    Primary,
    PrimaryOutline,
    Secondary
}

@Composable
fun TextButton(text: String, style: ButtonStyle = ButtonStyle.Primary, enabled: Boolean = true, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {

    val colors = when(style) {
        ButtonStyle.Primary -> ButtonDefaults.buttonColors(
            contentColor = colorScheme.secondary
        )
        ButtonStyle.Secondary -> ButtonDefaults.buttonColors(
            containerColor = colorScheme.secondary
        )
        ButtonStyle.PrimaryOutline -> ButtonDefaults.outlinedButtonColors()
    }

    val border = when(style) {
        ButtonStyle.PrimaryOutline -> BorderStroke(1.dp, color = colorScheme.primary)
        else -> null
    }

    Button(onClick, modifier,
        shape = AbsoluteRoundedCornerShape(15),
        colors = colors,
        border = border,
        enabled = enabled
    ) {
        Text(text = text, color = when(style) {
            ButtonStyle.Primary -> colorScheme.background
            ButtonStyle.PrimaryOutline -> colorScheme.primary
            ButtonStyle.Secondary -> colorScheme.background
        })
    }
}

@Preview(showBackground = true)
@Composable
private fun PrimaryTextButtonPreview() {
    TextButton(text = "Primary", style = ButtonStyle.Primary)
}

@Preview(showBackground = true)
@Composable
private fun PrimaryOutlineTextButtonPreview() {
    TextButton("Primary", ButtonStyle.PrimaryOutline)
}


@Preview(showBackground = true)
@Composable
private fun SecondaryTextButtonPreview() {
    TextButton("Secondary", ButtonStyle.Secondary)
}