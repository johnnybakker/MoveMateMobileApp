package nl.johnny.movemate.ui.components

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun TextDivider(text: String, height: Dp, color: Color) {
    Row(
        Modifier
            .height(height)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Divider(
            color = color,
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(0f)
                .weight(1f),
            thickness = 5.dp,
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = 5.dp),
            color = color
        )
        Divider(
            color = color,
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(0f)
                .weight(1f),
            thickness = 5.dp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextDividerPreview() {
    TextDivider("Divider", 30.dp, Color.Gray)
}