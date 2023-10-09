package nl.johnny.movemate.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.johnny.movemate.ui.components.ButtonStyle
import nl.johnny.movemate.ui.components.TextButton
import nl.johnny.movemate.ui.components.TextDivider
import nl.johnny.movemate.ui.components.TextField
import nl.johnny.movemate.ui.models.LoginViewModel
import nl.johnny.movemate.ui.theme.MoveMateTheme
import nl.johnny.movemate.ui.theme.MoveMateTheme.alertText

object LoginScreenTestTags {
    const val EmailInput = "EmailInput"
    const val PasswordInput = "PasswordInput"
    const val LoginButton = "LoginButton"
}

@Composable
fun LoginScreen(viewModel: LoginViewModel, onClickLogIn: () -> Unit, onClickSignUp: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "WELCOME", fontSize = 30.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            placeholder = "Email",
            modifier = Modifier.testTag(LoginScreenTestTags.EmailInput)
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            placeholder = "Password",
            secret = true,
            modifier = Modifier.testTag(LoginScreenTestTags.PasswordInput)
        )
        Spacer(modifier = Modifier.height(5.dp))

        TextButton(
            text = "Log in",
            onClick = onClickLogIn,
            style = ButtonStyle.Primary,
            modifier = Modifier.fillMaxWidth().testTag(LoginScreenTestTags.LoginButton),
        )

        TextDivider(
            text = "or",
            height = 100.dp,
            color = Color.LightGray
        )

        TextButton(
            text = "Sign up",
            onClick = onClickSignUp,
            style = ButtonStyle.PrimaryOutline,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "Log in", showBackground = true)
@Composable
private fun LogInScreenPreview() {
    MoveMateTheme {
        val loginModel = viewModel<LoginViewModel>()
        LoginScreen(loginModel, {}, {})
    }
}