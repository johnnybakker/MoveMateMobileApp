package nl.johnny.movemate.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.johnny.movemate.api.models.User
import nl.johnny.movemate.repositories.IUserRepository
import nl.johnny.movemate.ui.models.SignUpViewModel
import nl.johnny.movemate.ui.screens.SearchScreen
import nl.johnny.movemate.ui.screens.UserSearchItem
import nl.johnny.movemate.ui.theme.MoveMateTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList


class SearchActivity : MoveMateActivity() {

    private lateinit var users: SnapshotStateList<User>
    private lateinit var value: MutableState<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoveMateTheme(menuItems = menuItems) {
                users = remember { mutableStateListOf()  }
                value = remember { mutableStateOf("") }

                SearchScreen({ search(it) }, users.toList()) { user ->
                    UserSearchItem(
                        user = user,
                        isSubscription = user.subscribers.contains(app.userId),
                        subscribe = { subscribe(user) },
                        unsubscribe = { unsubscribe(user) }
                    )
                }



            }
            search("")
        }
    }


    private fun subscribe(user: User) {
        app.userRepository.subscribe(user.id){
            user.subscribers.add(app.userId)
            search(value.value)
        }
    }

    private fun unsubscribe(user: User) {
        app.userRepository.unsubscribe(user.id){
            user.subscribers.remove(app.userId)
            search(value.value)
        }
    }

    private fun search(v: String) {
        value.value = v
        if(v.isEmpty()) {
            app.userRepository.getAll {
                users.clear()
                users.addAll(it)
            }
        } else {
            app.userRepository.search(v) {
                users.clear()
                users.addAll(it)
            }
        }
    }

}