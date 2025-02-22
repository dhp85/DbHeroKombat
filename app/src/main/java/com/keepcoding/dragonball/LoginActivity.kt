package com.keepcoding.dragonball

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.keepcoding.dragonball.databinding.ActivityLoginBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// Declaración de la clase LoginActivity que hereda de AppCompatActivity
class LoginActivity : AppCompatActivity() {

    // Declaración de las variables que se usarán para el ViewModel y el enlace a las vistas
    private val viewModel: LoginViewModel by viewModels()  // ViewModel para manejar la lógica de inicio de sesión
    private lateinit var binding: ActivityLoginBinding  // Binding para acceder a las vistas definidas en el XML

    // Método onCreate que se ejecuta cuando la actividad es creada
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflamos el archivo de diseño XML usando ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // Establecemos el contenido de la actividad utilizando el binding (que contiene todas las vistas del layout)
        setContentView(binding.root)
        setObservers()
        // Método personalizado para habilitar el diseño de borde a borde en dispositivos con pantallas sin bordes
        enableEdgeToEdge()

        // Guardamos las credenciales (usuario y contraseña) en las SharedPreferences
        // "LoginPreferences" es el nombre del archivo donde se almacenan las credenciales
        // MODE_PRIVATE es el modo en que las preferencias son privadas para esta aplicación
        viewModel.saveCredentials(
            preferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE),
            user = binding.EditTextUser.text.toString(),  // El nombre de usuario para guardar
            password = binding.EditTextPassword.text.toString()  // La contraseña para guardar (es recomendable cifrarla en un entorno real)
        )
        // Encontramos el botón de inicio de sesión por su ID y lo asignamos a la variable buttonLogin
        //val buttonLogin = findViewById<Button>(R.id.loginButton)
        binding.loginButton.setOnClickListener{
            viewModel.login(
                user = binding.EditTextUser.text.toString(),
                password = binding.EditTextPassword.text.toString()
            )
        }
        // El siguiente paso sería configurar un OnClickListener para el botón y agregar lógica de autenticación
    }
    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when(state){
                    is LoginViewModel.State.Idle -> {}
                    is LoginViewModel.State.Loading -> {
                       binding.spinningLoading.visibility = View.VISIBLE
                    }
                    is LoginViewModel.State.Successs -> {
                        binding.spinningLoading.visibility = View.INVISIBLE
                        Toast.makeText(this@LoginActivity, "Ir a la siguiente pantalla", Toast.LENGTH_LONG).show()
                    }
                    is LoginViewModel.State.Error -> {
                        binding.spinningLoading.visibility = View.INVISIBLE
                        Toast.makeText(this@LoginActivity, "Ha ocurrido un error. ${state.message} ${state.errorCode}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}