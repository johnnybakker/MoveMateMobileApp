package nl.johnny.movemate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.tooltip.TooltipDrawable
import nl.johnny.movemate.ui.components.ButtonStyle
import nl.johnny.movemate.ui.components.TextButton
import nl.johnny.movemate.ui.components.TextDivider
import nl.johnny.movemate.ui.components.TextField
import nl.johnny.movemate.ui.models.SignUpViewModel
import nl.johnny.movemate.ui.theme.MoveMateTheme

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onClickSignUp: () -> Unit,
    onClickLogIn: () -> Unit
) {
    var repeatPassword by remember {
        mutableStateOf("")
    }

    val result = StrongPasswordValidator.validate(viewModel.password)
    val passwordsDoMatch = repeatPassword.compareTo(viewModel.password) == 0

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "SIGN UP", fontSize = 30.sp, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = viewModel.username,
                onValueChange = { viewModel.username = it },
                placeholder = "Username"
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                placeholder = "Email"
            )

            Spacer(modifier = Modifier.height(5.dp))


            TextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                placeholder = "Password",
                secret = true
            )

            Spacer(modifier = Modifier.height(5.dp))

            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(8.dp),

                color = when(result.level()){
                    StrongPasswordValidator.Level.None -> Color.LightGray
                    StrongPasswordValidator.Level.Strong -> nl.johnny.movemate.ui.theme.Success
                    StrongPasswordValidator.Level.Weak -> nl.johnny.movemate.ui.theme.Danger
                    StrongPasswordValidator.Level.Medium -> nl.johnny.movemate.ui.theme.Warning
                }
            )

//            if (viewModel.password.isNotEmpty()) {
//                if (!result.containsOneNumber) Text(text = "Doesn't contain 1 or more numbers")
//                if (!result.containsOneUpperCaseCharacter) Text(text = "Doesn't contain 1 or more uppercase characters")
//                if (!result.containsEightOrMoreCharacters) Text(text = "Doesn't contain 8 or more characters")
//                if (!result.containsThreeLowerCaseCharacters) Text(text = "Doesn't contain 3 or more lowercase characters")
//                if (!result.containsOneSpecialCharacter) Text(text = "Doesn't contain 1 ore more special characters")
//            }

            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = repeatPassword,
                onValueChange = { repeatPassword = it },
                placeholder = "Repeat password",
                secret = true
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextButton(
                text = "Sign up",
                onClick = onClickSignUp,
                style =  ButtonStyle.Primary,
                enabled =
                    viewModel.username.isNotEmpty() &&
                    viewModel.email.isNotEmpty() &&
                    viewModel.password.isNotEmpty() &&
                    passwordsDoMatch &&
                    result.valid(),
                modifier = Modifier.fillMaxWidth()
            )

            TextDivider(
                text = "or",
                height = 100.dp,
                color = Color.LightGray
            )

            TextButton(
                text = "Log in",
                onClick = onClickLogIn,
                style = ButtonStyle.PrimaryOutline,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private object StrongPasswordValidator {
    enum class Level {
        None,
        Weak,
        Medium,
        Strong
    }

    class ValidationResult(
        val containsThreeLowerCaseCharacters: Boolean,
        val containsOneUpperCaseCharacter: Boolean,
        val containsOneNumber: Boolean,
        val containsOneSpecialCharacter: Boolean,
        val containsEightOrMoreCharacters: Boolean
    ) {

        fun level() : Level {
            var trueCount = 0
            if(containsThreeLowerCaseCharacters) trueCount++
            if(containsOneUpperCaseCharacter) trueCount++
            if(containsOneNumber) trueCount++
            if(containsOneSpecialCharacter) trueCount++
            if(containsEightOrMoreCharacters) trueCount++

            val level = when(trueCount) {
                5 -> Level.Strong
                4,3 -> Level.Medium
                2,1 -> Level.Weak
                else -> Level.None
            }

            return level
        }

        fun valid() : Boolean = level() == Level.Strong
    }

    private val containsThreeLowerCaseCharactersRegex: Regex = Regex("^(?=(.*[a-z]){3,}).*\$")
    private val containsOneUpperCaseCharacterRegex = Regex("^(?=(.*[A-Z])+).*\$")
    private val containsOneNumberRegex = Regex("^(?=(.*[0-9])+).*\$")
    private val containsOneSpecialCharacterRegex = Regex("^(?=(.*[!@#\$%^&*()\\-__+.])+).*\$")
    private val containsEightOrMoreCharactersRegex = Regex("^.{8,}\$")

    fun validate(password: String) = ValidationResult(
        containsThreeLowerCaseCharactersRegex.matches(password),
        containsOneUpperCaseCharacterRegex.matches(password),
        containsOneNumberRegex.matches(password),
        containsOneSpecialCharacterRegex.matches(password),
        containsEightOrMoreCharactersRegex.matches(password),
    )
}

@Preview(name = "SIGN UP", showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    MoveMateTheme() {


        val loginModel = viewModel<SignUpViewModel>()
        SignUpScreen(loginModel, {}, {})
    }
}