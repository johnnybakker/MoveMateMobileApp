package nl.johnny.movemate.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import nl.johnny.movemate.ui.models.SignUpViewModel
import nl.johnny.movemate.ui.screens.LogInScreen
import nl.johnny.movemate.ui.theme.MoveMateTheme
import nl.johnny.movemate.ui.screens.SignUpScreen

class LogInActivity : ComponentActivity() {

    enum class Screen {
        LOGIN,
        SIGNUP
    }

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {

            var screen by remember { mutableStateOf(Screen.LOGIN) }

            MoveMateTheme {
                when(screen) {
                    Screen.LOGIN -> LogInScreen(
                        viewModel = viewModel,
                        onClickLogIn = { },
                        onClickSignUp = { screen = Screen.SIGNUP }
                    )
                    Screen.SIGNUP -> SignUpScreen(
                        viewModel = viewModel,
                        onClickSignUp = {},
                        onClickLogIn = { screen = Screen.LOGIN }
                    )
                }
            }
        }
    }
}