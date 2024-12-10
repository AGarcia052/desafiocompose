package com.alejandro.minidesafiocompose.loginyregistro


import android.widget.Toast
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
import androidx.navigation.NavController
import com.alejandro.minidesafiocompose.modelo.Usuario


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(nav: NavController){
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
                        modifier = Modifier.fillMaxWidth().padding(end = 10.dp)
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

            Registrar(nav,viewModel)

        }
    }

}
@Composable
fun Registrar(nav: NavController,viewModel: LoginRegistroViewModel){
    val context = LocalContext.current

    var correo by remember { mutableStateOf("") }
    var passwd by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    val comprobar = viewModel.comprobarCorreo


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
            onValueChange = { edad = it },
            label = { Text("Edad") },
        )
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
        )
        Spacer(Modifier.height(100.dp))

        Row(){
            Button(onClick = {
                viewModel.comprobarCorreo(correo)
                if(comprobar.value){
                    Toast.makeText(context, "El correo ya esta en uso", Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.registrarUsuario(Usuario(nombre,correo,edad.toInt(),passwd),context)
                }

            }) {
                Text(text = "Aceptar")
            }

            Spacer(modifier = Modifier.width(50.dp))
            Button(onClick = {  nav.navigate("Login") }) {
                Text(text = "Cancelar")
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRegistro(){
        Registro(nav = NavController(context = LocalContext.current))
}