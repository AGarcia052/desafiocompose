package com.alejandro.minidesafiocompose.loginyregistro

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alejandro.minidesafiocompose.Colecciones
import com.alejandro.minidesafiocompose.modelo.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginRegistroViewModel: ViewModel() {

    var db = Firebase.firestore
    var TAG = "Alejandro"

    private val _usuario = mutableStateOf<Usuario?>(null)
    val usuario get() = _usuario


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
                    null
                }

            }
            .addOnFailureListener {
                Log.e(TAG,"Error al obtener datos", it)
            }
    }

}