package com.alejandro.minidesafiocompose.principal.juego

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.minidesafiocompose.componentes.TextoNormal
import com.alejandro.minidesafiocompose.componentes.Titulo
import com.alejandro.minidesafiocompose.principal.Historial
import com.alejandro.minidesafiocompose.principal.Informacion
import com.alejandro.minidesafiocompose.ui.theme.MinidesafioComposeTheme


@Composable
fun Juego(correo: String) {
    MinidesafioComposeTheme {
        var tab by remember { mutableStateOf(2) }
        Scaffold(
            topBar = {
                TopBarJuego()
            },
            bottomBar = {
                BottomBarJuego(onAccion = {
                    when (it) {
                        1 -> {
                            tab = 1
                        }

                        2 -> {
                            tab = 2
                        }

                        3 -> {
                            tab = 3
                        }
                    }
                })
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                when (tab) {
                    1 -> Informacion(correo)
                    2 -> Jugar(correo)
                    3 -> Historial(correo)
                }
            }
        }
    }
}


@Composable
fun BottomBarJuego(onAccion: (Int) -> Unit) {

    BottomAppBar(
        actions = {

            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 30.dp)) {
                    IconButton(onClick = { onAccion(1) }) {
                        Icon(Icons.Filled.Info, contentDescription = "Perfil")
                    }
                    TextoNormal("Perfil")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(onClick = { onAccion(2) }) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = "Jugar")
                    }
                    TextoNormal("Jugar")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 30.dp)
                ) {
                    IconButton(onClick = { onAccion(3) }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Historial")
                    }
                    TextoNormal("Historial")
                }

            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarJuego() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Titulo("JUEGO")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewJuego() {
    Juego("")
}
