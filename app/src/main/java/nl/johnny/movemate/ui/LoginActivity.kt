package nl.johnny.movemate.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import nl.johnny.movemate.ui.models.SignUpViewModel
import nl.johnny.movemate.ui.screens.LoginScreen
import nl.johnny.movemate.ui.theme.MoveMateTheme
import nl.johnny.movemate.ui.screens.SignUpScreen
import nl.johnny.movemate.ui.theme.MoveMateTheme.alertText

class LoginActivity : MoveMateActivity() {

    enum class Screen {
        LOGIN,
        SIGNUP
    }

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app.userRepository.get({
            app.currentUser = it
            startActivity<MainActivity>()
       }, {
            setContent {
                var screen by remember { mutableStateOf(Screen.LOGIN) }

                MoveMateTheme {
                    when(screen) {
                        Screen.LOGIN -> LoginScreen(
                            viewModel = viewModel,
                            onClickLogIn = {
                                app.userRepository.login(
                                    email = viewModel.email,
                                    password = viewModel.password,
                                    onSuccess = {
                                        startActivity<MainActivity>()
                                    },
                                    onFailure = {
                                        alertText = "Wrong email or password combination"
                                    }
                                )
                            },
                            onClickSignUp = { screen = Screen.SIGNUP }
                        )
                        Screen.SIGNUP -> SignUpScreen(
                            viewModel = viewModel,
                            onClickSignUp = {
                                app.userRepository.signUp(
                                    username = viewModel.username,
                                    password = viewModel.password,
                                    email = viewModel.email,
                                    onSuccess = { screen = Screen.LOGIN },
                                    onFailure = {
                                        alertText = "Failed to sign up using the information below"
                                    }
                                )
                            },
                            onClickLogIn = { screen = Screen.LOGIN }
                        )
                    }
                }
            }
        })

    }
}