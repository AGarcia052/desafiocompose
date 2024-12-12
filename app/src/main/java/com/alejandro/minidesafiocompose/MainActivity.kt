package com.alejandro.minidesafiocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alejandro.minidesafiocompose.principal.juego.Juego
import com.alejandro.minidesafiocompose.loginyregistro.Login
import com.alejandro.minidesafiocompose.loginyregistro.Registro
import com.alejandro.minidesafiocompose.ui.theme.MinidesafioComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MinidesafioComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Rutas.login) {
                    composable(Rutas.login) {
                        Login(navController)
                    }
                    composable(Rutas.registro) {
                        Registro(navController)
                    }
                    composable("Juego/{correo}") { backStackEntry ->
                        val correo = backStackEntry.arguments?.getString("correo") ?: ""
                        Juego(correo)
                    }

                }
            }
        }
    }
}
