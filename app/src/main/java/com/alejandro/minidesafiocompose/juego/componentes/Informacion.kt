package com.alejandro.minidesafiocompose.juego.componentes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.minidesafiocompose.juego.InformacionViewModel
import com.alejandro.minidesafiocompose.modelo.Estado
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
        Column(modifier = Modifier.fillMaxSize().padding(top = 50.dp), verticalArrangement = Arrangement.Center) {

            DatosUsuario(usuario.value!!)
            Text("Partidas ganadas: ${partidasGanadas.value}")
            Text("Partidas jugadas: ${partidasJugadas.value}")
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TituloDatos("Nombre: ")
            ValorDatos(usuario.nombre)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            TituloDatos("Edad: ")
            ValorDatos(usuario.edad.toString())
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            TituloDatos("Correo: ")
            ValorDatos(usuario.correo)
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            TituloDatos("Contraseña: ")
            ValorDatos(usuario.passwd)
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
            TituloDatos("Partidas Jugadas: ")
            ValorDatos(partidasJugadas.toString())
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            TituloDatos("Partidas Ganadas: ")
            ValorDatos(partidasGanadas.toString())
        }


    }
}

@Composable
fun TituloDatos(texto: String) {
    Text(text = texto, Modifier.padding(end = 5.dp), fontSize = 22.sp)
}

@Composable
fun ValorDatos(texto: String) {
    Text(text = texto, fontSize = 16.sp)
}

@Composable
fun Titulo(texto: String){
    Text(text = texto, fontSize = 28.sp)
}

@Preview(showBackground = true)
@Composable
fun PreviewInformacion() {
    Column(modifier = Modifier.fillMaxSize().padding(top = 50.dp)) {
        DatosUsuario(Usuario("NOMBRE", "CORREO@CORREO.COM", 18, "PASSWD"))
        EstadisticasUsuario(10,5)
    }
}