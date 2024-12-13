package com.alejandro.minidesafiocompose.componentes

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.alejandro.minidesafiocompose.R


@Composable
fun TFPasswd(
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChange: (String) -> Unit,
    label: String = "Contraseña"
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(label) },
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_passwd_oculta),
                contentDescription = "Contraseña",
                modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
            )
        },
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewTFPasswd() {
    var prueba = "prueba"
    TFPasswd(password = prueba, onPasswordChange = { prueba = it })
}