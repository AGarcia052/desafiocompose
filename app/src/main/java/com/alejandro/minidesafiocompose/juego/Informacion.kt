package com.alejandro.minidesafiocompose.juego

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.minidesafiocompose.juego.componentes.Subtitulo
import com.alejandro.minidesafiocompose.juego.componentes.TextoNormal
import com.alejandro.minidesafiocompose.juego.componentes.Titulo
import com.alejandro.minidesafiocompose.modelo.Usuario

@Composable
fun Informacion(correoUsuario: String) {

    val viewModel = remember { InformacionViewModel() }
    val usuario = viewModel.usuario
    val partidasJugadas = viewModel.partidasJugadas
    val partidasGanadas = viewModel.partidasGanadas

    if (usuario.value == null) {
        viewModel.obtenerUsuario(correoUsuario)
        viewModel.obtenerSumPartidasGanadas(correoUsuario)
        viewModel.obtenerSumPartidasJugadas(correoUsuario)
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(top = 50.dp)) {

            DatosUsuario(usuario.value!!)
            EstadisticasUsuario(partidasJugadas.value,partidasGanadas.value)

        }
    }
}

@Composable
fun DatosUsuario(usuario: Usuario) {

    Row(horizontalArrangement = Arrangement.Center, modifier  = Modifier.fillMaxWidth()){
        Titulo("PERFIL")
    }
    Spacer(modifier = Modifier.height(35.dp))
    Column(modifier = Modifier.padding(start = 25.dp).fillMaxWidth()){
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Subtitulo("Nombre: ")
            TextoNormal(usuario.nombre)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Subtitulo("Edad: ")
            TextoNormal(usuario.edad.toString())
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Subtitulo("Correo: ")
            TextoNormal(usuario.correo)
        }
    }

}

@Composable
fun EstadisticasUsuario(partidasJugadas: Int, partidasGanadas: Int){
    Row(horizontalArrangement = Arrangement.Center, modifier  = Modifier.fillMaxWidth().padding(top = 50.dp)){
        Titulo("ESTADISTICAS")
    }
    Spacer(modifier = Modifier.height(35.dp))
    Column(modifier = Modifier.padding(start = 25.dp).fillMaxWidth()){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Subtitulo("Partidas Jugadas: ")
            TextoNormal(partidasJugadas.toString())
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Subtitulo("Partidas Ganadas: ")
            TextoNormal(partidasGanadas.toString())
        }


    }
}



@Preview(showBackground = true)
@Composable
fun PreviewInformacion() {
    Column(modifier = Modifier.fillMaxSize().padding(top = 50.dp)) {
        DatosUsuario(Usuario("NOMBRE", "CORREO@CORREO.COM", 18, "PASSWD"))
        EstadisticasUsuario(10,5)
    }
}