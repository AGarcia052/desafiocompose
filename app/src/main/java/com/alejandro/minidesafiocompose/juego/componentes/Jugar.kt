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
import com.alejandro.minidesafiocompose.modelo.Estado
import com.alejandro.minidesafiocompose.modelo.Partida
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
            ComenzarPartida(dificultad = dif,correo = correo) {
                jugar = false
            }
        }

    }


}

@Composable
fun ComenzarPartida(dificultad: Dificultad, correo: String, volver: () -> Unit) {

    var ronda by remember { mutableStateOf(1) }
    var puntosMaquina by remember { mutableStateOf(0) }
    var puntosUsuario by remember { mutableStateOf(0) }

    var eleccionUsuario: Tirada by remember { mutableStateOf(Tirada.NADA) }
    var tiradaRealizada by remember { mutableStateOf(false) }
    val viewModel = remember { JugarViewModel() }
    val tiradaMaquina = viewModel.tiradaMaquina

    var resultado by remember { mutableStateOf(-1) }
    var finalRonda by remember { mutableStateOf(false) }

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

        Eleccion(onEleccionCambiada = { eleccionUsuario = it })

        Row(Modifier.padding(top = 20.dp, bottom = 20.dp)) {
            Text(text = "Seleccionado: ", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = eleccionUsuario.toString(), fontSize = 20.sp)
        }

        if (resultado == -1) {
            Button(onClick = { tiradaRealizada = true }, enabled = eleccionUsuario != Tirada.NADA) {
                Text(text = "JUGAR")
            }
        } else {
            if (puntosMaquina < 3 && puntosUsuario < 3) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    when (resultado) {
                        1 -> TextResultado("HAS GANADO")
                        2 -> TextResultado("HAS PERDIDO")
                        else -> TextResultado("EMPATE")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = {
                        viewModel.reiniciarTirada()
                        resultado = -1
                        eleccionUsuario = Tirada.NADA
                        tiradaRealizada = false
                        ronda++
                    }
                    ) {
                        Text("Siguiente ronda")
                    }
                }
            }else{
                Row(verticalAlignment = Alignment.CenterVertically){
                    if (puntosMaquina == 3){
                        TextResultado("DERROTA")
                    }
                    else{
                        TextResultado("VICTORIA")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(onClick = {
                        viewModel.guardarPartida(
                            Partida(
                            usuario = correo,
                            puntosMaquina = puntosMaquina,
                            puntosUsuario = puntosUsuario,
                            dificultad = dificultad.valor,
                            estado = if (puntosMaquina == 3) Estado.PERDIDA.valor else Estado.GANADA.valor
                        )
                        )
                        volver()
                    }
                    ) {
                        Text("REGRESAR")
                    }
                }

            }
        }
        if (finalRonda) {
            when (resultado) {
                1 -> {

                    puntosUsuario++
                }

                2 -> {

                    puntosMaquina++
                }

                else -> {

                }
            }
            finalRonda = false
        }


        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Maquina (${dificultad})", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(40.dp))
        if (tiradaMaquina.value != null) {
            EleccionMaquina(tiradaMaquina.value!!)
        }
        if (tiradaMaquina.value != null && resultado == -1) {
            resultado = resolverRonda(
                tiradaMaquina = tiradaMaquina.value!!,
                tiradaUsuario = eleccionUsuario
            )
            finalRonda = true
        }
        if (tiradaMaquina.value == null && tiradaRealizada) {
            viewModel.ejecutarRonda(eleccionUsuario, dificultad)
        }

    }
}

@Composable
fun TextResultado(resultado: String) {
    Text(text = resultado)
}

private fun resolverRonda(tiradaMaquina: Tirada, tiradaUsuario: Tirada): Int {

    val resultado: Int
    if (tiradaMaquina == Tirada.PIEDRA && tiradaUsuario == Tirada.PAPEL) {
        resultado = 1
    } else if (tiradaMaquina == Tirada.PIEDRA && tiradaUsuario == Tirada.TIJERA) {
        resultado = 2

    } else if (tiradaMaquina == Tirada.PAPEL && tiradaUsuario == Tirada.TIJERA) {
        resultado = 1
    } else if (tiradaMaquina == Tirada.PAPEL && tiradaUsuario == Tirada.PIEDRA) {
        resultado = 2
    } else if (tiradaMaquina == Tirada.TIJERA && tiradaUsuario == Tirada.PIEDRA) {
        resultado = 1
    } else if (tiradaMaquina == Tirada.TIJERA && tiradaUsuario == Tirada.PAPEL) {
        resultado = 2
    } else resultado = 0

    return resultado

}

@Composable
fun EleccionMaquina(eleccion: Tirada) {
    when (eleccion) {
        Tirada.PIEDRA -> {
            Image(
                painter = painterResource(R.drawable.piedra),
                contentDescription = "Piedra",
                modifier = Modifier.height(100.dp)
            )
        }

        Tirada.PAPEL -> {
            Image(
                painter = painterResource(R.drawable.papel),
                contentDescription = "Papel",
                modifier = Modifier.height(100.dp)
            )
        }

        Tirada.TIJERA -> {
            Image(
                painter = painterResource(R.drawable.tijera),
                contentDescription = "Tijera",
                modifier = Modifier.height(100.dp)
            )
        }

        else -> {}
    }
}

@Composable
fun Eleccion(onEleccionCambiada: (Tirada) -> Unit) {


    Row {
        Column {
            Image(
                painter = painterResource(R.drawable.piedra),
                contentDescription = "Piedra",
                modifier = Modifier
                    .height(100.dp)
                    .clickable {
                        onEleccionCambiada(Tirada.PIEDRA)
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
                        onEleccionCambiada(Tirada.PAPEL)
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
                        onEleccionCambiada(Tirada.TIJERA)
                    }
            )
        }
    }

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
        ComenzarPartida(Dificultad.FACIL,""){}
    }
}