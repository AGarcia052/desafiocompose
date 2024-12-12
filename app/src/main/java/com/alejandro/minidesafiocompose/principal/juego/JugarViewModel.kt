package com.alejandro.minidesafiocompose.principal.juego

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alejandro.minidesafiocompose.Colecciones
import com.alejandro.minidesafiocompose.modelo.Dificultad
import com.alejandro.minidesafiocompose.modelo.Partida
import com.alejandro.minidesafiocompose.modelo.Tirada
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class JugarViewModel: ViewModel() {

    val db = Firebase.firestore
    val TAG = "JUGARVW"

    private val _tiradaMaquina = mutableStateOf<Tirada?>(null)
    val tiradaMaquina get() = _tiradaMaquina



    fun ejecutarRonda(eleccionUsuario: Tirada, dificultad: Dificultad){
        when (dificultad) {
            Dificultad.FACIL -> {
                when (eleccionUsuario) {
                    Tirada.TIJERA -> {
                        when((1..6).random()){
                            1,2,3 -> _tiradaMaquina.value = Tirada.PAPEL
                            4,5 -> _tiradaMaquina.value = Tirada.TIJERA
                            else -> _tiradaMaquina.value = Tirada.PIEDRA
                        }
                    }
                    Tirada.PAPEL -> {
                        when((1..6).random()){
                            1,2,3 -> _tiradaMaquina.value = Tirada.PIEDRA
                            4,5 -> _tiradaMaquina.value = Tirada.PAPEL
                            else -> _tiradaMaquina.value = Tirada.TIJERA

                        }
                    }
                    Tirada.PIEDRA -> {
                        when((1..6).random()){
                            1,2,3 -> _tiradaMaquina.value = Tirada.TIJERA
                            4,5 -> _tiradaMaquina.value = Tirada.PIEDRA
                            else -> _tiradaMaquina.value = Tirada.PAPEL

                        }
                    }
                    else -> _tiradaMaquina.value = null
                }

            }
            Dificultad.DIFICIL -> {
                when (eleccionUsuario) {
                    Tirada.TIJERA -> {
                        when((1..6).random()){
                            1,2,3 -> _tiradaMaquina.value = Tirada.PIEDRA
                            4,5 -> _tiradaMaquina.value = Tirada.TIJERA
                            else -> _tiradaMaquina.value = Tirada.PAPEL
                        }
                    }
                    Tirada.PAPEL -> {
                        when((1..6).random()){
                            1,2,3 -> _tiradaMaquina.value = Tirada.TIJERA
                            4,5 -> _tiradaMaquina.value = Tirada.PAPEL
                            else -> _tiradaMaquina.value = Tirada.PIEDRA

                        }
                    }
                    Tirada.PIEDRA -> {
                        when((1..6).random()){
                            1,2,3 -> _tiradaMaquina.value = Tirada.PAPEL
                            4,5 -> _tiradaMaquina.value = Tirada.PIEDRA
                            else -> _tiradaMaquina.value = Tirada.TIJERA
                        }
                    }
                    else -> _tiradaMaquina.value = null
                }

            }
            else -> {
                when ((1..3).random()){
                    1 -> _tiradaMaquina.value = Tirada.PIEDRA
                    2 -> _tiradaMaquina.value = Tirada.PAPEL
                    else -> _tiradaMaquina.value = Tirada.TIJERA
                }
            }
        }
    }

    fun guardarPartida(partida: Partida){
        db.collection(Colecciones.partidas)
            .document()
            .set(partida)
            .addOnSuccessListener {
                Log.d(TAG, "Partida guardada")
            }
            .addOnFailureListener {
                Log.e(TAG, "Error al guardar partida")
            }
    }

    fun reiniciarTirada(){
        _tiradaMaquina.value = null
    }

}