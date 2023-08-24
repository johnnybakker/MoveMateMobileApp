package nl.johnny.movemate.ui

import android.content.Intent
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import nl.johnny.movemate.repositories.IRepositoryBinder
import nl.johnny.movemate.ui.models.SignUpViewModel
import nl.johnny.movemate.ui.screens.LogInScreen
import nl.johnny.movemate.ui.theme.MoveMateTheme
import nl.johnny.movemate.ui.screens.SignUpScreen

class LogInActivity : BaseActivity() {

    enum class Screen {
        LOGIN,
        SIGNUP
    }

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onRepositoryBinder(binder: IRepositoryBinder) {

        val userRepository = binder.getUserRepository(this)

        userRepository.get({ startMainActivity() }, {
            setContent {
                var screen by remember { mutableStateOf(Screen.LOGIN) }

                MoveMateTheme {
                    when(screen) {
                        Screen.LOGIN -> LogInScreen(
                            viewModel = viewModel,
                            onClickLogIn = {
                                userRepository.login(
                                    email = viewModel.email,
                                    password = viewModel.password,
                                    onSuccess = { startMainActivity() },
                                    onFailure = {  }
                                )
                            },
                            onClickSignUp = { screen = Screen.SIGNUP }
                        )
                        Screen.SIGNUP -> SignUpScreen(
                            viewModel = viewModel,
                            onClickSignUp = {
                                userRepository.signUp(
                                    username = viewModel.username,
                                    password = viewModel.password,
                                    email = viewModel.email,
                                    onSuccess = { screen = Screen.LOGIN },
                                    onFailure = {}
                                )
                            },
                            onClickLogIn = { screen = Screen.LOGIN }
                        )
                    }
                }
            }
        })
    }

    private fun startMainActivity() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
}