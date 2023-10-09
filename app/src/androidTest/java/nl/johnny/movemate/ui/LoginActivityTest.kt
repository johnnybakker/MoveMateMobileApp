package nl.johnny.movemate.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import nl.johnny.movemate.helpers.ColorHelper
import nl.johnny.movemate.ui.models.SignUpViewModel
import nl.johnny.movemate.ui.screens.LoginScreen
import nl.johnny.movemate.ui.screens.LoginScreenTestTags
import nl.johnny.movemate.ui.screens.SignUpScreen
import nl.johnny.movemate.ui.screens.SignUpScreenTestTags
import nl.johnny.movemate.ui.theme.Blue
import nl.johnny.movemate.ui.theme.Danger
import nl.johnny.movemate.ui.theme.LightGray
import nl.johnny.movemate.ui.theme.MoveMateTheme
import nl.johnny.movemate.ui.theme.Success
import nl.johnny.movemate.ui.theme.Warning
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test


class LoginActivityTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testPasswordFeedbackUI() {

        rule.setContent {
            val model by remember { mutableStateOf(SignUpViewModel()) }

            model.username = ""
            model.password = ""
            model.email = ""

            MoveMateTheme {
                SignUpScreen(viewModel = model, onClickSignUp = { }) {


                }
            }
        }

        val usernameInputNode = rule.onNode(hasSetTextAction() and hasTestTag(SignUpScreenTestTags.EmailInput))
        val emailInputNode = rule.onNode(hasSetTextAction() and hasTestTag(SignUpScreenTestTags.UsernameInput))
        val passwordInputNode = rule.onNode(hasSetTextAction() and hasTestTag(SignUpScreenTestTags.PasswordInput))
        val repeatPasswordInputNode = rule.onNode(hasSetTextAction() and hasTestTag(SignUpScreenTestTags.RepeatPasswordInput))
        val indicator = rule.onNodeWithTag(SignUpScreenTestTags.PasswordIndicator)
        val signUpButton = rule.onNodeWithTag(SignUpScreenTestTags.SignUpButton)

        usernameInputNode.performTextInput("Johnny")

        emailInputNode.performTextInput("johnny@test.nl")
        assertEquals(LightGray, ColorHelper.readColor(indicator))

        passwordInputNode.performTextInput("Johnny")
        assertEquals(Danger, ColorHelper.readColor(indicator))

        passwordInputNode.performTextInput("123")
        assertEquals(Warning, ColorHelper.readColor(indicator))

        passwordInputNode.performTextInput("!")
        assertEquals(Success, ColorHelper.readColor(indicator))

        signUpButton.assertHasClickAction()
        assertNotEquals(Blue, ColorHelper.readColor(signUpButton))
        repeatPasswordInputNode.performTextInput("Johnny123!")
        assertEquals(Blue, ColorHelper.readColor(signUpButton))
    }

    @Test
    fun testLoginUIInput() {

        rule.setContent {
            val model by remember { mutableStateOf(SignUpViewModel()) }

            model.username = ""
            model.password = ""
            model.email = ""

            MoveMateTheme {
                LoginScreen(viewModel = model, onClickLogIn = { }, onClickSignUp = {})
            }
        }

        val emailInputNode = rule.onNode(hasSetTextAction() and hasTestTag(LoginScreenTestTags.EmailInput))
        val passwordInputNode = rule.onNode(hasSetTextAction() and hasTestTag(LoginScreenTestTags.PasswordInput))
        val loginButtonNode = rule.onNode(hasTestTag(LoginScreenTestTags.LoginButton))

        emailInputNode.performTextInput("johnny@test.nl")
        passwordInputNode.performTextInput("Johnny123!")
        loginButtonNode.assertHasClickAction()
    }
}