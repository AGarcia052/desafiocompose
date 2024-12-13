package com.alejandro.minidesafiocompose.principal.passwd
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alejandro.minidesafiocompose.Rutas
import com.alejandro.minidesafiocompose.componentes.TFPasswd
import com.alejandro.minidesafiocompose.componentes.TextoNormal
import com.alejandro.minidesafiocompose.componentes.Titulo

@Composable
fun Passwd(correo: String, nav: NavController) {

    val viewModel  = remember { PasswdViewModel() }
    var passwd by remember { mutableStateOf("") }
    var repPasswd by remember { mutableStateOf("") }
    val contexto = LocalContext.current
    val resUpdate = viewModel.resUpdatePasswd


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Titulo("CAMBIAR CONTRASEÑA")
        Spacer(modifier = Modifier.height(120.dp))
        TFPasswd(password = passwd, onPasswordChange = { passwd = it })
        Spacer(modifier = Modifier.height(70.dp))
        TFPasswd(
            password = repPasswd,
            onPasswordChange = { repPasswd = it },
            label = "Repetir contraseña"
        )
        Spacer(modifier = Modifier.height(70.dp))
        Row(){
            Button(onClick = {
                if (comprobarPasswd(passwd, repPasswd, contexto)) {
                    viewModel.cambiarPasswd(correo, passwd)
                }
            }) {
                TextoNormal("Cambiar contraseña")
            }
            Spacer(Modifier.width(25.dp))
            Button(onClick = {nav.navigate("Juego/$correo")}) {
                TextoNormal("Volver")
            }
        }

    }

    if (resUpdate.value != null) {
        if (resUpdate.value == true) {
            Toast.makeText(contexto, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
            nav.navigate("Juego/$correo")
        } else {
            Toast.makeText(contexto, "Error al actualizar contraseña", Toast.LENGTH_SHORT).show()
        }
        viewModel.resetResUpdatePasswd()
    }

}

private fun comprobarPasswd(passwd: String, repPasswd: String, contexto: Context): Boolean {
    var mensaje = ""
    var valido = false
    if (passwd.isEmpty() || repPasswd.isEmpty()) {
        mensaje = "Rellena todos los campos"
    } else if (passwd.length < 6) {
        mensaje = "La contraseña debe tener al menos 6 caracteres"
    } else if (passwd != repPasswd) {
        mensaje = "Las contraseñas no coinciden"
    } else {
        valido = true
    }

    if (mensaje.isNotEmpty()) {
        Toast.makeText(contexto,mensaje,Toast.LENGTH_SHORT).show()
    }

    return valido
}
