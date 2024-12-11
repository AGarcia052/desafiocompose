package com.alejandro.minidesafiocompose.juego.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.alejandro.minidesafiocompose.juego.BottomBarJuego
import com.alejandro.minidesafiocompose.juego.TopBarJuego
import com.alejandro.minidesafiocompose.ui.theme.MinidesafioComposeTheme

@Composable
fun Jugar(){
    MinidesafioComposeTheme {

        Text("JUGAR")

    }

}


@Preview(showBackground = true)
@Composable
fun PreviewJugar(){
    Jugar()
}