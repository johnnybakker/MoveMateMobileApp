package nl.johnny.movemate.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.johnny.movemate.R
import nl.johnny.movemate.ui.components.IconButton
import nl.johnny.movemate.ui.theme.MoveMateTheme

@Composable
fun WorkoutDataRow(iconId: Int, text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = colorScheme.tertiary,
            modifier= Modifier
                .height(40.dp)
                .padding(bottom = 3.dp)
        )
        Text(
            text = text,
            color = colorScheme.tertiary,
            fontSize = 20.sp,
        )
    }
}

@Composable
fun WorkoutScreen(time: String, distance: String, speed: String,  started: Boolean, toggle: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        Text("WORKOUT", fontSize = 32.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        WorkoutDataRow(R.drawable.stopwatch_solid, time)
        WorkoutDataRow(R.drawable.person_running_solid, distance)
        WorkoutDataRow(R.mipmap.logo_no_spacing_foreground, speed)
        Spacer(Modifier.height(20.dp))
        when(started) {
            true -> IconButton (
                icon = R.drawable.square_solid,
                text = "STOP WORKOUT",
                color = colorScheme.tertiary,
                size = 28.sp
            ) {
                toggle()
            }
            false -> IconButton(
                icon = R.drawable.play_solid,
                text = "START WORKOUT",
                color = colorScheme.tertiary,
                size = 28.sp
            ) {
                toggle()
            }
        }
    }


}


@Composable
@Preview("WorkoutScreen")
fun WorkoutScreenPreview() {
    MoveMateTheme {
        Column {
            WorkoutScreen("00:00:00", "0.0 KM", "0.0 KM/H",false, {})
            WorkoutScreen("01:23:45", "23.4 KM", "5.9 KM/H", true, {})
        }
    }
}