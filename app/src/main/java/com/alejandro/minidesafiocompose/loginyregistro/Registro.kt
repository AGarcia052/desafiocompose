package com.alejandro.minidesafiocompose.loginyregistro


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alejandro.minidesafiocompose.R
import com.alejandro.minidesafiocompose.modelo.Usuario
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(nav: NavController) {
    val viewModel = remember { LoginRegistroViewModel() }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp)
                    ) {
                        Text("Registro")

                    }

                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(Modifier.height(10.dp))

            Registrar(nav, viewModel)

        }
    }

}

@Composable
fun Registrar(nav: NavController, viewModel: LoginRegistroViewModel) {
    val context = LocalContext.current

    var correo by remember { mutableStateOf("") }
    var passwd by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    val comprobar = viewModel.comprobarCorreo
    var showDatePicker by remember{ mutableStateOf(false)}
    var passwdVisible by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
        )
        Spacer(Modifier.height(100.dp))
        TextField(
            value = edad,
            onValueChange = {  },
            label = { Text("Edad") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendario),
                    contentDescription = "Seleccionar edad",
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            },
        )
        if (showDatePicker) {

            DatePickerModalInput(
                onDateSelected = { selectedDateMillis ->
                    selectedDateMillis?.let {
                        val calendar = Calendar.getInstance()
                        calendar.timeInMillis = it
                        val anio = calendar.get(Calendar.YEAR)
                        val anioActual = Calendar.getInstance().get(Calendar.YEAR)
                        edad = (anioActual - anio).toString()
                    }
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false },
                context = LocalContext.current
            )

        }
        Spacer(Modifier.height(100.dp))
        TextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
        )
        Spacer(Modifier.height(100.dp))
        TextField(
            value = passwd,
            onValueChange = { passwd = it },
            label = { Text("Contraseña") },
            visualTransformation = if (passwdVisible){
                VisualTransformation.None} else {PasswordVisualTransformation()},
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_passwd_oculta),
                    contentDescription = "Contraseña",
                    modifier = Modifier.clickable { passwdVisible = !passwdVisible }
                )}
        )
        Spacer(Modifier.height(100.dp))

        Row() {
            Button(onClick = {
                if (comprobarDatos(correo, passwd, nombre, context)){
                    viewModel.comprobarCorreo(correo)
                    if (comprobar.value) {
                        Toast.makeText(context, "El correo ya esta en uso", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.registrarUsuario(
                            Usuario(nombre, correo, edad.toInt(), passwd),
                            context
                        )
                        nav.navigate("Login")
                    }
                }
            }) {
                Text(text = "Aceptar")
            }

            Spacer(modifier = Modifier.width(50.dp))
            Button(onClick = { nav.navigate("Login") }) {
                Text(text = "Cancelar")
            }

        }

    }
}



private fun comprobarDatos(correo: String, passwd: String, nombre: String, context: Context):Boolean{
    var valido = true
    val correoRegex = Regex("^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    if (correo.isEmpty() || passwd.isEmpty() || nombre.isEmpty()){
        Toast.makeText(context, "Rellena todos los campos para continuar", Toast.LENGTH_SHORT).show()
        valido = false
    }
    else if (!correoRegex.matches(correo)){
        Toast.makeText(context, "El correo no tiene un formato valido", Toast.LENGTH_LONG).show()
        valido = false
    }
    else if (passwd.length < 6){
        Toast.makeText(context, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
        valido = false
    }
    return valido

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    context: Context
) {


    val datePickerState = rememberDatePickerState (initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                val dateMillis = datePickerState.selectedDateMillis
                val fechaSeleccionada = Calendar.getInstance()
                fechaSeleccionada.timeInMillis = dateMillis!!
                var fechaActual = Calendar.getInstance()
                fechaActual = fechaActual.apply { timeInMillis = System.currentTimeMillis() }
                fechaActual.add(Calendar.YEAR, -18)
                if (fechaSeleccionada.before(fechaActual)){
                    onDateSelected(dateMillis)
                }
                else{
                    Toast.makeText(context, "Debes ser mayor de edad", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRegistro() {
    Registro(nav = NavController(context = LocalContext.current))
}