package com.alejandro.minidesafiocompose.loginyregistro

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.alejandro.minidesafiocompose.Rutas
import com.alejandro.minidesafiocompose.ui.theme.MinidesafioComposeTheme

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun Login(nav: NavController) {
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
                        modifier = Modifier.fillMaxWidth().padding(end = 10.dp)
                    ) {
                        Text("Iniciar sesion")

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
            Registrarse(nav)
            Spacer(Modifier.height(40.dp))

            Column{
                Loguearse(nav)
            }

        }
    }
}

@Composable
fun Registrarse(nav: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text("¿Aun no tienes una cuenta?", fontSize = 15.sp)
            Spacer(Modifier.width(10.dp))
            Button(onClick = { nav.navigate(Rutas.registro) }) {
                Text("Registrate")
            }
        }


    }
}

@Composable
fun Loguearse(nav: NavController) {

    var correo by remember { mutableStateOf("") }
    var passwd by remember { mutableStateOf("") }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
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
        )
        Spacer(Modifier.height(100.dp))
        Button(onClick = { iniciarSesion(correo, passwd)}) {
            Text(text = "Iniciar sesion")
        }

    }

}

private fun iniciarSesion(correo: String, passwd: String) {
    val viewModel = LoginRegistroViewModel()
    var usuarios = viewModel.resLogin

    viewModel.login(correo, passwd)

    if (usuarios.value != null) {
        if (passwd == usuarios.value!!.passwd){
            Log.i("Alejandro","INICIO DE SESION CORRECTO")
            // NAVEGAR JUEGO
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MinidesafioComposeTheme {
        Login(nav = NavController(context = LocalContext.current))
    }
}