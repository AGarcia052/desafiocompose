package com.alejandro.minidesafiocompose.principal.passwd

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alejandro.minidesafiocompose.Colecciones
import com.alejandro.minidesafiocompose.modelo.Usuario
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class PasswdViewModel: ViewModel() {
    val db = Firebase.firestore
    val TAG = "CONTRASEÑAVM"

    private val _resUpdatePasswd = mutableStateOf<Boolean?>(null)
    val resUpdatePasswd get() = _resUpdatePasswd



    fun cambiarPasswd(correo: String, passwd: String) {
        db.collection(Colecciones.usuarios)
            .document(correo)
            .update("passwd", passwd)
            .addOnSuccessListener {
                Log.i(TAG,"Contraseña actualizada")
                _resUpdatePasswd.value = true
            }
            .addOnFailureListener {
                Log.e(TAG,"Error al actualizar contraseña", it)
                _resUpdatePasswd.value = false
            }
    }

    fun resetResUpdatePasswd(){
        _resUpdatePasswd.value = null
    }

}