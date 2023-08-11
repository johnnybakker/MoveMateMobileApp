package nl.johnny.movemate.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.johnny.movemate.ui.components.ButtonStyle
import nl.johnny.movemate.ui.components.TextButton
import nl.johnny.movemate.ui.components.TextDivider
import nl.johnny.movemate.ui.components.TextField
import nl.johnny.movemate.ui.models.LogInViewModel

@Composable
fun LogInScreen(viewModel: LogInViewModel, onClickLogIn: () -> Unit, onClickSignUp: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Welcome", fontSize = 30.sp)
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

            TextButton(
                text = "Log in",
                onClick = onClickLogIn,
                style = ButtonStyle.Primary,
                modifier = Modifier.fillMaxWidth()
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
}

@Preview(name = "Log in", showBackground = true)
@Composable
private fun LogInScreenPreview() {
    val loginModel = viewModel<LogInViewModel>()
    LogInScreen(loginModel, {}, {})
}