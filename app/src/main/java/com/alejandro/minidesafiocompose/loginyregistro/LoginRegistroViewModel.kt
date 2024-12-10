package com.alejandro.minidesafiocompose.loginyregistro

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alejandro.minidesafiocompose.Colecciones
import com.alejandro.minidesafiocompose.modelo.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginRegistroViewModel: ViewModel() {

    var db = Firebase.firestore
    var TAG = "Alejandro"

    private val _resLogin = MutableLiveData<Usuario>()
    val resLogin: LiveData<Usuario> = _resLogin


    fun login(correo: String, passwd: String) {
        var usuario: Usuario
        db.collection(Colecciones.usuarios)
            .document(correo)
            .get()
            .addOnSuccessListener { documento ->
                try{
                    usuario = Usuario(
                        nombre = documento.getString("nombre").orEmpty(),
                        correo = documento.id,
                        edad = (documento["edad"] as? Long)?.toInt() ?: 0,
                        passwd = documento.getString("passwd").orEmpty()
                    )
                    _resLogin.value = usuario
                    Log.i(TAG,"Datos obtenidos: $usuario")

                }catch (e: Exception){
                    Log.e(TAG, "Error parsing document: ${documento.id}", e)
                    null
                }

            }
    }

}