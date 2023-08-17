package nl.johnny.movemate.ui

import android.content.Intent
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

        if(userRepository.isLoggedIn()) {
            return startMainActivity()
        }

        setContent {
            var screen by remember { mutableStateOf(Screen.LOGIN) }

            MoveMateTheme {
                when(screen) {
                    Screen.LOGIN -> LogInScreen(
                        viewModel = viewModel,
                        onClickLogIn = {
                            userRepository.logIn(
                                email = viewModel.email,
                                password = viewModel.password,
                                onSuccess = { startMainActivity() }
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
                                onSuccess = { screen = Screen.LOGIN }
                            )
                        },
                        onClickLogIn = { screen = Screen.LOGIN }
                    )
                }
            }
        }
    }

    private fun startMainActivity() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
}