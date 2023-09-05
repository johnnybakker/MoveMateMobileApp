package nl.johnny.movemate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.johnny.movemate.R
import nl.johnny.movemate.ui.theme.MoveMateTheme

enum class IconButtonType {
    IconLeftTextRight,
    IconRightTextLeft
}

@Composable
fun IconButton(
    icon: Int,
    text: String = "",
    type: IconButtonType = IconButtonType.IconRightTextLeft,
    color: Color = MaterialTheme.colorScheme.background,
    size: TextUnit = 20.sp,
    onClick: () -> Unit)
{
    Button(onClick = onClick) {
        Row(
            horizontalArrangement = Arrangement.spacedBy((size.value*.5).dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if(type == IconButtonType.IconRightTextLeft && text.isNotEmpty()) {
                Text(
                    text = text,
                    color = color,
                    fontSize = size,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
            }
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                Modifier
                    .background(Color.Transparent)
                    .height((size.value*1.2).dp),
                tint = color
            )
            if(type == IconButtonType.IconLeftTextRight && text.isNotEmpty()) {
                Text(
                    text = text,
                    color = color,
                    fontSize = size,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
@Preview("Icon button")
fun IconButtonPreview() {
    MoveMateTheme() {
        Column() {
            IconButton(
                icon = R.drawable.play_solid,
                text = "START WORKOUT",
                type = IconButtonType.IconRightTextLeft,
                size = 28.sp
            ) {

            }
            IconButton(
                icon = R.drawable.square_solid,
                text = "STOP WORKOUT",
                type = IconButtonType.IconLeftTextRight
            ) {

            }
        }
    }

}