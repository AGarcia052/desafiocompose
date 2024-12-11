package com.alejandro.minidesafiocompose.juego.componentes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Jugar(correo: String) {

    var jugar by remember { mutableStateOf(false) }
    var dificultad by remember { mutableStateOf(0f) }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!jugar){
            Spacer(modifier = Modifier.height(200.dp))
            Row{
                Text(text = "Dificultad: ")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = when(dificultad){
                    0f -> "Facil"
                    1f -> "Normal"
                    2f -> "Dificil"
                    else -> "Facil"
                })
            }
            Slider(value = dificultad, onValueChange = { dificultad = it }, valueRange = 0f..2f, steps = 1, modifier = Modifier.width(200.dp))
            Spacer(modifier = Modifier.height(150.dp))
            Button(onClick = {jugar = true}, shape = ButtonDefaults.elevatedShape) {
                Text(text = "COMENZAR PARTIDA")
            }
        }else{
            Partida(dificultad)
        }

    }



}

@Composable
fun Partida(dificultad: Float){

}


@Preview(showBackground = true)
@Composable
fun PreviewJugar() {
    Jugar("")
}