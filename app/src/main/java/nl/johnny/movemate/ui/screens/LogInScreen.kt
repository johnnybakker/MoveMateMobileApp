package nl.johnny.movemate.ui

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
import nl.johnny.movemate.ui.components.TextButton
import nl.johnny.movemate.ui.components.TextDivider
import nl.johnny.movemate.ui.components.TextField

@Composable
fun LogInScreen(loginViewModel: LoginViewModel, onClickLogIn: () -> Unit, onClickSignUp: () -> Unit) {
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
            TextField(loginViewModel.email, { loginViewModel.email = it }, "Email")
            Spacer(modifier = Modifier.height(5.dp))
            TextField(loginViewModel.password, { loginViewModel.password = it }, "Password", true)
            Spacer(modifier = Modifier.height(5.dp))

            TextButton("Log in", onClickLogIn, Modifier.fillMaxWidth())
            TextDivider("or", height = 30.dp, color = Color.LightGray)
            TextButton("Sign up", onClickSignUp, Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(200.dp))
        }
    }
}

@Preview(name = "Log in", showBackground = true)
@Composable
private fun LogInScreenPreview() {
    val loginModel = viewModel<LoginViewModel>()
    LogInScreen(loginModel, {}, {})
}