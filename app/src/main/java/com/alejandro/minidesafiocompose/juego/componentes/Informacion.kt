package com.alejandro.minidesafiocompose.juego.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alejandro.minidesafiocompose.juego.InformacionViewModel

@Composable
fun Informacion(correoUsuario: String){

    val viewModel = remember {InformacionViewModel()}
    val usuario = viewModel.usuario
    val partidasJugadas = viewModel.partidasJugadas
    val partidasGanadas = viewModel.partidasGanadas

    if (usuario.value == null){
        viewModel.obtenerUsuario(correoUsuario)
        viewModel.obtenerSumPartidasGanadas(correoUsuario)
        viewModel.obtenerSumPartidasJugadas(correoUsuario)
    }
    else{
        Column (modifier = Modifier.fillMaxSize()){
            Text("Nombre: ${usuario.value?.nombre}")
            Text("Correo: ${usuario.value?.correo}")
            Text("Edad: ${usuario.value?.edad}")
            Text("Partidas ganadas: ${partidasGanadas.value}")
            Text("Partidas jugadas: ${partidasJugadas.value}")
        }
    }
}

@Preview
@Composable
fun PreviewInformacion(){
    Informacion("efe")
}