package com.alejandro.minidesafiocompose.juego.componentes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Subtitulo(texto: String) {
    Text(text = texto, Modifier.padding(end = 5.dp), fontSize = 22.sp)
}

@Composable
fun TextoNormal(texto: String) {
    Text(text = texto, fontSize = 16.sp)
}

@Composable
fun Titulo(texto: String){
    Text(text = texto, fontSize = 28.sp)
}