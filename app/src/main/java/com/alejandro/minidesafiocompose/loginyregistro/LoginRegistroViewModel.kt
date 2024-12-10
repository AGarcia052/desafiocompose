package com.alejandro.minidesafiocompose.loginyregistro

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alejandro.minidesafiocompose.Colecciones
import com.alejandro.minidesafiocompose.modelo.Usuario
import com.alejandro.minidesafiocompose.modelo.UsuarioFB
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginRegistroViewModel: ViewModel() {

    var db = Firebase.firestore
    var TAG = "Alejandro"

    private val _usuario = mutableStateOf<Usuario?>(null)
    val usuario get() = _usuario

    private val _comprobarCorreo = mutableStateOf(false)
    val comprobarCorreo get() = _comprobarCorreo



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
                Log.e(TAG,"Error al obtener datos", it)
            }
    }

    fun comprobarCorreo(correo: String){
        db.collection(Colecciones.usuarios)
            .document(correo)
            .get()
            .addOnSuccessListener { documento ->
                if (documento.exists()){
                    _comprobarCorreo.value = true
                }
            }
    }
    fun registrarUsuario(usuario: Usuario,context: Context){

        db.collection(Colecciones.usuarios)
            .document(usuario.correo)
            .set(UsuarioFB(usuario.nombre,usuario.edad,usuario.passwd))
            .addOnSuccessListener {
                Log.i(TAG,"Usuario registrado")
                Toast.makeText(context, "Registro completado con exito", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.e(TAG,"Error al registrar usuario",it)
                Toast.makeText(context, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show()
            }
    }

}