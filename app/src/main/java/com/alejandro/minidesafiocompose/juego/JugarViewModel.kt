package com.alejandro.minidesafiocompose.juego

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alejandro.minidesafiocompose.modelo.Dificultad
import com.alejandro.minidesafiocompose.modelo.Tirada

class JugarViewModel: ViewModel() {

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

    fun reiniciarTirada(){
        _tiradaMaquina.value = null
    }

}