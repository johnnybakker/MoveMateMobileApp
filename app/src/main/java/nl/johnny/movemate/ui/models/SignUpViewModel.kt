package nl.johnny.movemate.ui.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable

@Serializable
class SignUpViewModel : LogInViewModel() {
    var username by mutableStateOf("")
}