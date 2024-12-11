package com.alejandro.minidesafiocompose.juego

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.alejandro.minidesafiocompose.Colecciones
import com.alejandro.minidesafiocompose.modelo.Estado
import com.alejandro.minidesafiocompose.modelo.Partida
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class HistorialViewModel: ViewModel() {

    val db = Firebase.firestore
    val TAG = "HISTORIALVW"

    private val _partidasGanadas = mutableStateListOf<Partida>()
    val partidasGanadas: List<Partida> get() = _partidasGanadas

    private val _partidasPerdidas = mutableStateListOf<Partida>()
    val partidasPerdidas: List<Partida> get() = _partidasPerdidas


    fun obtenerPartidasGanadas(correo: String){

        db.collection(Colecciones.partidas)
            .whereEqualTo("usuario",correo)
            .whereEqualTo("estado",Estado.GANADA.valor)
            .get()
            .addOnSuccessListener { resultado ->
                val partidas = resultado.documents.mapNotNull { documento ->
                    try{
                        Partida(
                            correoUsuario = documento.getString("usuario").orEmpty(),
                            puntosMaquina = (documento.getLong("puntos_maquina"))?.toInt() ?: -1,
                            puntosUsuario = (documento.getLong("puntos_usuario"))?.toInt() ?: -1,
                            estado = Estado.GANADA
                        )
                    }catch (e: Exception){
                        Log.e(TAG, "Error parsing document: ${documento.id}", e)
                        null
                    }
                }
                _partidasGanadas.clear()
                _partidasGanadas.addAll(partidas)
                Log.i(TAG,"Datos obtenidos: $partidas")
            }
            .addOnFailureListener {
                Log.e(TAG, "Error al obtener los datos", it)

            }
    }

    fun obtenerPartidasPerdidas(correo: String){

        db.collection(Colecciones.partidas)
            .whereEqualTo("usuario",correo)
            .whereEqualTo("estado",Estado.PERDIDA.valor)
            .get()
            .addOnSuccessListener { resultado ->
                val partidas = resultado.documents.mapNotNull { documento ->
                    try{
                        Partida(
                            correoUsuario = documento.getString("usuario").orEmpty(),
                            puntosMaquina = (documento.get("puntos_maquina") as? Long)?.toInt() ?: -1,
                            puntosUsuario = (documento.get("puntos_usuario") as? Long)?.toInt() ?: -1,
                            estado = Estado.PERDIDA
                        )
                    }catch (e: Exception){
                        Log.e(TAG, "Error parsing document: ${documento.id}", e)
                        null
                    }
                }
                _partidasPerdidas.clear()
                _partidasPerdidas.addAll(partidas)
                Log.i(TAG,"Datos obtenidos: $partidas")
            }
            .addOnFailureListener {
                Log.e(TAG, "Error al obtener los datos", it)

            }
    }


}