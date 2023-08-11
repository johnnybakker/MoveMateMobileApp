package nl.johnny.movemate.ui.components


import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun TextField(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier, placeholder: String = "", secret: Boolean = false, valid: Boolean = true) {

    val style = TextStyle(
        fontSize = typography.bodyLarge.fontSize,
        color = when(value.isEmpty()) {
            false -> Color.Black
            true -> Color.LightGray
        },
        textDecoration = TextDecoration.None

    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = style,
        modifier = modifier.border(
            width = 2.dp,
            color = when(valid) {
                true -> Color.LightGray
                false -> Color.Red
            },
            shape = RoundedCornerShape(size = 5.dp)
        ),
        visualTransformation = when(secret){
            false -> VisualTransformation.None
            true -> PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Password
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )  {
                value.ifEmpty { Text(placeholder, style = style) }
                innerTextField()
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TextFieldPreview() {
    val value by remember { mutableStateOf("") }
    TextField(value = value, onValueChange = {}, placeholder = "Placeholder")
}