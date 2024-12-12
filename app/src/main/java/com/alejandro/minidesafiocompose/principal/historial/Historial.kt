package com.alejandro.minidesafiocompose.principal.historial

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alejandro.minidesafiocompose.componentes.Subtitulo
import com.alejandro.minidesafiocompose.componentes.TextoNormal
import com.alejandro.minidesafiocompose.modelo.Dificultad
import com.alejandro.minidesafiocompose.modelo.Partida
import com.alejandro.minidesafiocompose.ui.theme.MinidesafioComposeTheme

@Composable
fun Historial(correo: String) {
    var ganadas = true
    var viewModel = remember { HistorialViewModel() }

    MinidesafioComposeTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Subtitulo("Filtrar por:")
                Spacer(modifier = Modifier.width(6.dp))
                ganadas = radioButtons()
            }

            RecyclerView(ganadas, viewModel, correo)

        }
    }

}

@Composable
fun RecyclerView(ganadas: Boolean, viewModel: HistorialViewModel, correo: String) {

    val partidasGanadas = viewModel.partidasGanadas
    val partidasPerdidas = viewModel.partidasPerdidas

    if (ganadas) {
        viewModel.obtenerPartidasGanadas(correo)

    } else {
        viewModel.obtenerPartidasPerdidas(correo)
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        items(
            if (ganadas) {
                partidasGanadas
            } else {
                partidasPerdidas
            }
        ) {
            ListaPartidas(p = it)
        }
    }
}


@Composable
fun ListaPartidas(p: Partida) {
    Card(
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 1.dp, bottom = 1.dp, start = 3.dp, end = 1.dp),
        shape = RectangleShape,
    )
    {
        Column(
            modifier = Modifier.padding(all= 5.dp).fillMaxWidth()
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Subtitulo("Dificultad: ")
                TextoNormal(Dificultad.fromValor(p.dificultad).toString())
            }
            Column(modifier = Modifier.padding(start = 20.dp,top = 15.dp)){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Subtitulo("Tu puntuación: ")
                    TextoNormal(p.puntosUsuario.toString())
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Subtitulo("Puntuación oponente: ")
                    TextoNormal(p.puntosMaquina.toString())
                }
            }

        }

    }
}

@Composable
fun radioButtons(): Boolean {
    var ganadas by remember { mutableStateOf(true) }
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(selected = ganadas, onClick = { ganadas = true }
        )
        TextoNormal("Ganadas")

        Spacer(Modifier.width(27.dp))

        RadioButton(selected = !ganadas, onClick = { ganadas = false }
        )
        TextoNormal("Perdidas")
    }
    return ganadas
}

@Preview(showBackground = true)
@Composable
fun PreviewHistorial() {
    ListaPartidas(Partida("prueba@prueba.com", 1, 2, 3, 2f))
}