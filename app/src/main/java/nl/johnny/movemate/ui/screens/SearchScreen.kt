package nl.johnny.movemate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.johnny.movemate.R
import nl.johnny.movemate.api.models.User
import nl.johnny.movemate.ui.components.TextField
import nl.johnny.movemate.ui.theme.MoveMateTheme


object SearchScreenTestTags {
    const val SearchInput = "SearchInput"
    const val SubscribeButton = "SubscribeButton"
    const val UnSubscribeButton = "UnSubscribeButton"
    const val UsernameText = "UsernameText"
}

@Composable
fun <T> SearchScreen(
    search: (String) -> Unit,
    results: List<T>,
    template: @Composable() (T) -> Unit
) {
    var searchValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        TextField(
            value = searchValue,
            onValueChange = {
                searchValue = it
                search(searchValue)
            },
            placeholder = "Search",
            modifier = Modifier.testTag(SearchScreenTestTags.SearchInput)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(0.dp, 5.dp)
        ) {
            results.forEach {
                template(it)
            }
        }
    }

}

@Composable
fun UserSearchItem(
    user: User,
    isSubscription: Boolean,
    subscribe: (id: Int) -> Unit,
    unsubscribe: (id: Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 4.dp)
    ) {
        Text(user.username, modifier = Modifier.padding(10.dp).testTag(SearchScreenTestTags.UsernameText))
        when(isSubscription) {
            true -> Button(onClick = { unsubscribe(user.id) }, Modifier.testTag(SearchScreenTestTags.UnSubscribeButton)) {
                Icon(
                    painter = painterResource(R.drawable.user_check_solid),
                    contentDescription = null,
                    Modifier.background(Color.Transparent).height(20.dp),
                    tint = colorScheme.background
                )
            }
            false -> Button(onClick = { subscribe(user.id) }, Modifier.testTag(SearchScreenTestTags.SubscribeButton)) {
                Icon(
                    painter = painterResource(R.drawable.user_plus_solid),
                    contentDescription = null,
                    Modifier.background(Color.Transparent).height(20.dp),
                    tint = colorScheme.background
                )
            }
        }
    }
    Divider()
}

@Preview(name = "Search", showBackground = true)
@Composable
private fun SearchPreview() {
    val results = remember {
        mutableStateListOf(
            User(id = 1, username = "Johnny", email = "", subscribers = mutableListOf(), subscriptions = mutableListOf()),
            User(id = 2, username = "Johnny", email = "", subscribers = mutableListOf(), subscriptions = mutableListOf()),
        )
    }

    MoveMateTheme {
        SearchScreen({}, results) {
            UserSearchItem(user = it, it.id % 2 == 0, {}, {})
        }
    }
}