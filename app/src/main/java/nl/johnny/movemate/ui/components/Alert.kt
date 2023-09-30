package nl.johnny.movemate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import nl.johnny.movemate.ui.theme.MoveMateTheme
import nl.johnny.movemate.ui.theme.MoveMateTheme.alertError
import nl.johnny.movemate.ui.theme.MoveMateTheme.alertSuccess
import nl.johnny.movemate.ui.theme.MoveMateTheme.alertText
import nl.johnny.movemate.ui.theme.MoveMateTheme.alertWarning
import kotlin.concurrent.thread

@Composable
fun Alert(text: String, color: Color) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            //.clip(RoundedCornerShape(percent = 10))
            //.background(Color(0x7F474747))

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x01474747))
                .shadow(elevation = 0.dp, shape = RoundedCornerShape(percent = 10))
                .padding(1.dp)


        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 10))
                    .background(color)
                    .wrapContentHeight()
                    .fillMaxWidth(1f),
                contentAlignment = Alignment.Center,
                propagateMinConstraints = true

            ) {
                Text(text = text, color = colorScheme.tertiary, modifier = Modifier.padding(20.dp, 20.dp))
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertPreview() {
    MoveMateTheme {
        alertSuccess("This is a warning")
    }

}

