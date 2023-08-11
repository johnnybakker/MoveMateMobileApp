package nl.johnny.movemate.ui.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class LogInViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
}