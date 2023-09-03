package nl.johnny.movemate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.johnny.movemate.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import nl.johnny.movemate.ui.theme.MoveMateTheme


@Composable
fun TopBar(title: String, icon: Painter) {


    Box(
        modifier = Modifier
            .background(colorScheme.primary)
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                Modifier.height(80.dp)
            )
            Text(
                text = title,
                fontSize = 28.sp,
                color = colorScheme.secondary
            )
        }
    }
}

@Composable
@Preview(name= "TopBar Preview", showBackground = true)
fun TopBarPreview() {
    MoveMateTheme {

    }
}