package nl.johnny.movemate.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import nl.johnny.movemate.api.models.User
import nl.johnny.movemate.helpers.TimeHelper
import nl.johnny.movemate.ui.screens.SearchScreen
import nl.johnny.movemate.ui.screens.SearchScreenTestTags
import nl.johnny.movemate.ui.screens.UserSearchItem
import nl.johnny.movemate.ui.theme.MoveMateTheme
import org.junit.Rule
import org.junit.Test

class SearchActivityTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testSearchResults() {

        rule.setContent {


            val userId = 1

            val users: SnapshotStateList<User> = remember {
                mutableStateListOf(
                    User(id = 1, username = "Johnny", email = "", subscribers = mutableListOf(2), subscriptions = mutableListOf(3)),
                    User(id = 2, username = "Jan", email = "", subscribers = mutableListOf(), subscriptions = mutableListOf(1)),
                    User(id = 3, username = "Lesley", email = "", subscribers = mutableListOf(1), subscriptions = mutableListOf()),
                )
            }

            val selection: SnapshotStateList<User> = mutableStateListOf()
            selection.addAll(users)

            fun search(v: String) {
                selection.clear()
                selection.addAll(users.filter { u -> u.username.lowercase().contains(v.lowercase()) })
            }

            MoveMateTheme {
                SearchScreen(search = { search(it) }, results = selection) {
                    UserSearchItem(
                        user = it,
                        isSubscription = it.subscriptions.contains(userId),
                        subscribe = {},
                        unsubscribe = {}
                    )
                }
            }
        }

        TimeHelper.delay(rule, 1000)
        rule.onAllNodesWithTag(SearchScreenTestTags.UsernameText).assertCountEquals(3)
        rule.onAllNodesWithTag(SearchScreenTestTags.SubscribeButton).assertCountEquals(2)
        rule.onAllNodesWithTag(SearchScreenTestTags.UnSubscribeButton).assertCountEquals(1)

        rule.onNodeWithTag(SearchScreenTestTags.SearchInput)
            .performTextInput("J")
        TimeHelper.delay(rule, 1000)

        rule.onAllNodesWithTag(SearchScreenTestTags.UsernameText).assertCountEquals(2)
        rule.onAllNodesWithTag(SearchScreenTestTags.SubscribeButton).assertCountEquals(1)
        rule.onAllNodesWithTag(SearchScreenTestTags.UnSubscribeButton).assertCountEquals(1)
    }
}