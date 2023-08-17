package nl.johnny.movemate.ui

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import nl.johnny.movemate.repositories.IRepositoryBinder
import nl.johnny.movemate.ui.theme.MoveMateTheme

class MainActivity : BaseActivity() {
    override fun onRepositoryBinder(binder: IRepositoryBinder) {

        val userRepository = binder.getUserRepository(this)



        setContent {

            var result by remember { mutableStateOf("") }

            MoveMateTheme {
                Column {
                    Text("Hello, world! ${userRepository.isLoggedIn()} $result")
                    Button(onClick = {
                        userRepository.getAll { result = it }
                    }) {
                        Text(text = "Get")
                    }
                    Button(onClick = {
                        userRepository.logOut()
                        finish()
                    }) {
                        Text(text = "Logout")
                    }
                }
            }
        }
    }

}