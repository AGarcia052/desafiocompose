package com.alejandro.minidesafiocompose.juego

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alejandro.minidesafiocompose.modelo.Usuario

class InformacionViewModel: ViewModel() {

    private val _usuario = mutableStateOf<Usuario?>(null)
    val usuario get() = _usuario
    

}