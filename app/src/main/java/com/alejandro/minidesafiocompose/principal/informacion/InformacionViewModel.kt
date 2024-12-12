package com.alejandro.minidesafiocompose.principal.informacion

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alejandro.minidesafiocompose.Colecciones
import com.alejandro.minidesafiocompose.modelo.Estado
import com.alejandro.minidesafiocompose.modelo.Usuario
import com.google.firebase.Firebase
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.firestore

class InformacionViewModel: ViewModel() {

    val db = Firebase.firestore
    val TAG = "INFORMACIONVW"
    private val _usuario = mutableStateOf<Usuario?>(null)
    val usuario get() = _usuario

    private val _partidasJugadas = mutableStateOf(-1)
    val partidasJugadas get() = _partidasJugadas

    private val _partidasGanadas = mutableStateOf(-1)
    val partidasGanadas get() = _partidasGanadas

    fun obtenerUsuario(correo: String) {
        var usuario: Usuario
        db.collection(Colecciones.usuarios)
            .document(correo)
            .get()
            .addOnSuccessListener { documento ->
                try{
                    usuario = Usuario(
                        nombre = documento.getString("nombre").orEmpty(),
                        correo = documento.id,
                        edad = (documento.get("edad") as? Long)?.toInt() ?: 0,
                        passwd = documento.getString("passwd").orEmpty()
                    )
                    _usuario.value = usuario
                    Log.i(TAG,"Datos obtenidos: $usuario")

                }catch (e: Exception){
                    Log.e(TAG, "Error parsing document: ${documento.id}", e)
                    _usuario.value = null
                }

            }
            .addOnFailureListener {
                Log.e("INFORMACIONVW","Error al obtener datos", it)
            }
    }

    fun obtenerSumPartidasJugadas(correo: String){
        val query = db.collection(Colecciones.partidas).whereEqualTo("usuario",correo)
        val contPartidas = query.count()
        contPartidas.get(AggregateSource.SERVER).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                Log.d(TAG, "Partidas Jugadas: ${snapshot.count}")
                _partidasJugadas.value = snapshot.count.toInt()
            } else {
                Log.d(TAG, "Count failed: ", task.getException())
            }
        }



    }

    fun obtenerSumPartidasGanadas(correo: String){
        val query = db.collection(Colecciones.partidas).whereEqualTo("usuario",correo).whereEqualTo("estado",Estado.GANADA.valor)
        val contPartidas = query.count()
        contPartidas.get(AggregateSource.SERVER).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                Log.d(TAG, "Partidas Ganadas: ${snapshot.count}")
                _partidasGanadas.value = snapshot.count.toInt()
            } else {
                Log.d(TAG, "Count failed: ", task.getException())
            }
        }
    }


}