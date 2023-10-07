package nl.johnny.movemate.ui

import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import junit.framework.Assert
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import nl.johnny.movemate.ui.models.SignUpViewModel
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
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test


class LoginActivityTest {

    @get:Rule
    val rule = createComposeRule()

    private val emailInputField = hasSetTextAction() and hasTestTag(SignUpScreenTestTags.EmailInput)
    private val usernameInputField = hasSetTextAction() and hasTestTag(SignUpScreenTestTags.UsernameInput)
    private val passwordInputField = hasSetTextAction() and hasTestTag(SignUpScreenTestTags.PasswordInput)
    private val repeatPasswordInputField = hasSetTextAction() and hasTestTag(SignUpScreenTestTags.RepeatPasswordInput)

    @Test
    fun invalidPassword() {



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

        val usernameInputNode = rule.onNode(usernameInputField)
        val emailInputNode = rule.onNode(emailInputField)
        val passwordInputNode = rule.onNode(passwordInputField)
        val repeatPasswordInputNode = rule.onNode(repeatPasswordInputField)
        val indicator = rule.onNodeWithTag(SignUpScreenTestTags.PasswordIndicator)
        val signUpButton = rule.onNodeWithTag(SignUpScreenTestTags.SignUpButton)

        usernameInputNode.performTextInput("Johnny")

        emailInputNode.performTextInput("johnny@test.nl")
        assertEquals(LightGray, readColor(indicator))

        passwordInputNode.performTextInput("Johnny")
        assertEquals(Danger, readColor(indicator))

        passwordInputNode.performTextInput("123")
        assertEquals(Warning, readColor(indicator))

        passwordInputNode.performTextInput("!")
        assertEquals(Success, readColor(indicator))

        signUpButton.assertHasClickAction()
        assertNotEquals(Blue, readColor(signUpButton))
        repeatPasswordInputNode.performTextInput("Johnny123!")
        assertEquals(Blue, readColor(signUpButton))
    }

    private fun readColor(node: SemanticsNodeInteraction): Color {
        val color = IntArray(1)
        val image = node.captureToImage()

        image.readPixels(color,
            image.width / 4, image.height / 2, 1, 1)

        return Color(color[0])
    }
}