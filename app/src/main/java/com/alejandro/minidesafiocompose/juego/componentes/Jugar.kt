package com.alejandro.minidesafiocompose.juego.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.minidesafiocompose.R
import com.alejandro.minidesafiocompose.juego.JugarViewModel
import com.alejandro.minidesafiocompose.modelo.Dificultad
import com.alejandro.minidesafiocompose.modelo.Tirada

@Composable
fun Jugar(correo: String) {

    var jugar by remember { mutableStateOf(false) }
    var dificultad by remember { mutableStateOf(Dificultad.FACIL.valor) }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!jugar) {
            Spacer(modifier = Modifier.height(200.dp))
            Row {
                Text(text = "Dificultad: ")
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = when (dificultad) {
                        Dificultad.FACIL.valor -> Dificultad.FACIL.toString()
                        Dificultad.NORMAL.valor -> Dificultad.NORMAL.toString()
                        else -> Dificultad.DIFICIL.toString()
                    }
                )
            }
            Slider(
                value = dificultad,
                onValueChange = { dificultad = it },
                valueRange = 0f..2f,
                steps = 1,
                modifier = Modifier.width(200.dp)
            )
            Spacer(modifier = Modifier.height(150.dp))
            Button(onClick = { jugar = true }, shape = ButtonDefaults.elevatedShape) {
                Text(text = "COMENZAR PARTIDA")
            }
        } else {
            val dif = when (dificultad) {
                Dificultad.FACIL.valor -> Dificultad.FACIL
                Dificultad.NORMAL.valor -> Dificultad.NORMAL
                else -> Dificultad.DIFICIL
            }
            Partida(dif)
        }

    }


}

@Composable
fun Partida(dificultad: Dificultad) {

    var ronda by remember { mutableStateOf(1) }
    var puntosMaquina by remember { mutableStateOf(0) }
    var puntosUsuario by remember { mutableStateOf(0) }
    var eleccionUsuario: Tirada by remember { mutableStateOf(Tirada.NADA) }
    var tiradaRealizada by remember { mutableStateOf(false) }
    val viewModel = remember { JugarViewModel() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        Text(text = "Ronda: $ronda", fontSize = 40.sp)
        Spacer(modifier = Modifier.height(15.dp))
        Row() {
            Text(text = "Puntos maquina: $puntosMaquina")
            Spacer(modifier = Modifier.width(40.dp))
            Text(text = "Tus puntos: $puntosUsuario")
        }
        Spacer(modifier = Modifier.height(40.dp))

        eleccionUsuario = eleccion()

        Row(Modifier.padding(top = 20.dp, bottom = 20.dp)){
            Text(text = "Seleccionado: ",fontSize = 20.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = eleccionUsuario.toString(),fontSize = 20.sp)
        }

        Button(onClick = {tiradaRealizada = true}) {
            Text(text = "JUGAR")
        }

        Spacer(modifier = Modifier.height(80.dp))

        Text(text = "Maquina (${dificultad})", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(150.dp))
    }
    if (tiradaRealizada){

        viewModel.ejecutarRonda(eleccionUsuario,dificultad)
        tiradaRealizada = false
        
    }


}



@Composable
fun eleccion(): Tirada{

    var eleccionUsuario: Tirada by remember { mutableStateOf(Tirada.NADA) }

    Row{
        Column {
            Image(
                painter = painterResource(R.drawable.piedra),
                contentDescription = "Piedra",
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        eleccionUsuario = Tirada.PIEDRA
                    },

                )
        }
        Column {
            Spacer(modifier = Modifier.width(15.dp))
            Image(
                painter = painterResource(R.drawable.papel),
                contentDescription = "Papel",
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        eleccionUsuario = Tirada.PAPEL
                    }
            )
        }
        Column {
            Spacer(modifier = Modifier.width(15.dp))
            Image(
                painter = painterResource(R.drawable.tijera),
                contentDescription = "Tijera",
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        eleccionUsuario = Tirada.TIJERA
                    }
            )
        }
    }
    return eleccionUsuario
}

@Preview(showBackground = true)
@Composable
fun PreviewJugar() {
    Jugar("")
}

@Preview(showBackground = true)
@Composable
fun PreviewPartida() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Partida(Dificultad.FACIL)
    }
}