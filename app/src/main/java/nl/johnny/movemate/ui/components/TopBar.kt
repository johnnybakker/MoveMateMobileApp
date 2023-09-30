package nl.johnny.movemate.ui.components

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.johnny.movemate.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.sp
import com.google.firebase.annotations.concurrent.Background
import nl.johnny.movemate.ui.theme.MoveMateTheme
import nl.johnny.movemate.ui.theme.White
import java.util.Locale


@Composable
fun TopBar(title: String) {


    Box(
        modifier = Modifier
            .background(colorScheme.primary)
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize(unbounded = true, align = Alignment.Center)
                    .clip(CircleShape)
                    .background(colorScheme.background)
                    .padding(0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.logo_no_spacing_foreground),
                    contentDescription = null,
                    modifier = Modifier.height(80.dp)
                )
            }

            Text(
                text = title.uppercase(Locale.ROOT),
                fontSize = 32.sp,
                color = colorScheme.background,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
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