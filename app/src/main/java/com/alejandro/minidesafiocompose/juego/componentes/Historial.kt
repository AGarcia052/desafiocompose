package com.alejandro.minidesafiocompose.juego.componentes

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alejandro.minidesafiocompose.juego.HistorialViewModel
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
                    .height(100.dp)
            )
            {
                ganadas = radioButtons()
            }

            RecyclerView(ganadas, viewModel, correo)

        }
    }

}

@Composable
fun RecyclerView(ganadas: Boolean, viewModel: HistorialViewModel, correo: String) {

    var partidasGanadas = viewModel.partidasGanadas
    var partidasPerdidas = viewModel.partidasPerdidas

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
            .padding(top = 1.dp, bottom = 1.dp, start = 1.dp, end = 1.dp),
        shape = RectangleShape
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Puntos maquina: ")
            Text(
                text = p.puntosMaquina.toString(), modifier = Modifier
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.width(50.dp))
            Text(text = "Tus puntos: ")
            Text(
                text = p.puntosUsuario.toString(),
                modifier = Modifier
                    .padding(2.dp)
            )
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
        Text(text = "Ganadas")

        Spacer(Modifier.width(50.dp))

        RadioButton(selected = !ganadas, onClick = { ganadas = false }
        )
        Text(text = "Perdidas")
    }
    return ganadas
}

@Preview(showBackground = true)
@Composable
fun PreviewHistorial() {
    Historial("efe")
}