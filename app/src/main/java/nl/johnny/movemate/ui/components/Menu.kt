package nl.johnny.movemate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.johnny.movemate.R
import nl.johnny.movemate.ui.theme.MoveMateTheme

data class MenuItem(val name: String, val icon: Int, val onClick: () -> Unit = {})

@Composable
fun MenuButton(item: MenuItem, modifier: Modifier = Modifier) {
    Button(
        onClick = item.onClick,
        shape= RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.primary
        ),
        contentPadding =  PaddingValues(top = 5.dp, bottom = 5.dp),
        modifier = modifier.wrapContentHeight()

    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
                .padding(5.dp)
        ) {
            Icon(
                painter = painterResource(id =  item.icon),
                contentDescription = null,
                tint = colorScheme.background,
                modifier = Modifier
                    .height(25.dp)
                    .padding(0.dp)

            )
            Text(
                text = item.name,
                color = colorScheme.background,
                modifier = Modifier.padding(
                    top = 5.dp
                )
            )
        }
    }
}

@Composable
fun Menu(menuItems: List<MenuItem> = listOf()) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(colorScheme.secondary)
            .fillMaxWidth()
    ) {
        menuItems.forEach {
            MenuButton(item = it, Modifier.weight(1.0f))
        }
    }
}

@Preview(name = "Menu", showBackground = true)
@Composable
fun MenuPreview() {

    val items = listOf(
        MenuItem("Login", R.drawable.user_solid),
        MenuItem("Search", R.drawable.search)
    )

    MoveMateTheme(menuItems = items) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)){

        }
    }

}