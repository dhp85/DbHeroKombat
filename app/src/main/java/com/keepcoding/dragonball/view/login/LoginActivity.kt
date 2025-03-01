package com.keepcoding.dragonball.view.login


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.keepcoding.dragonball.databinding.ActivityLoginBinding
import com.keepcoding.dragonball.view.home.HerosKombatActivity
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()
        enableEdgeToEdge()

        // Recuperamos las credenciales guardadas (si existen) y el estado de "Remember Me"
        val preferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE)
        val credentials = viewModel.getCredentials(preferences)
        val username = credentials.first
        val password = credentials.second
        val rememberMe = viewModel.getRememberMeState(preferences)

        // Agregamos logs para verificar si las credenciales y estado de "Remember Me" se están recuperando correctamente
        Log.d("LoginActivity", "Recuperando credenciales guardadas: Usuario=$username, Contraseña=$password, RememberMe=$rememberMe")

        // Si las credenciales existen y "Remember Me" está marcado, los rellenamos en los campos
        if (rememberMe && username != "" && password != "") {
            binding.EditTextUser.setText(username)
            binding.EditTextPassword.setText(password)
            binding.rememberUser.isChecked = true
        }

        // Manejo de clics en el botón de login
        binding.loginButton.setOnClickListener {
            val user = binding.EditTextUser.text.toString()
            val password = binding.EditTextPassword.text.toString()
            val rememberMeChecked = binding.rememberUser.isChecked

            // Llamamos al método de login
            viewModel.login(user, password)

            // Si el usuario marca "Remember Me", guardamos las credenciales
            if (rememberMeChecked) {
                viewModel.saveCredentials(
                    preferences = preferences,
                    user = user,
                    password = password,
                    rememberMe = true
                )
            } else {
                viewModel.clearCredentials(preferences) // Limpiamos las credenciales si no se marca "Remember Me"
            }
        }

        // Si el usuario marca o desmarca "Remember Me", guardamos el estado
        binding.rememberUser.setOnCheckedChangeListener { _, isChecked ->
            val user = binding.EditTextUser.text.toString()
            val password = binding.EditTextPassword.text.toString()

            // Guardamos el estado de "Remember Me"
            viewModel.saveCredentials(
                preferences = preferences,
                user = user,
                password = password,
                rememberMe = isChecked
            )
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is LoginViewModel.State.Idle -> {}
                    is LoginViewModel.State.Loading -> {
                        loadingSettings()
                    }
                    is LoginViewModel.State.Successs -> {
                        successSettings()
                        startJuegoActivity()
                    }
                    is LoginViewModel.State.Error -> {
                        errorSettings()
                        Toast.makeText(
                            this@LoginActivity,
                            "Ha ocurrido un error. ${state.message} ${state.errorCode}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun startJuegoActivity() {
        HerosKombatActivity.startJuegoActivity(this)
        finish()
    }

    // FUNTIONS SETTINGS VIEWS
    private fun loadingSettings() {
        binding.spinningLoading.visibility = View.VISIBLE
        binding.loginButton.visibility = View.GONE
    }

    private fun successSettings() {
        binding.spinningLoading.visibility = View.GONE
        binding.loginButton.visibility = View.VISIBLE
    }

    private fun errorSettings() {
        binding.spinningLoading.visibility = View.GONE
        binding.loginButton.visibility = View.VISIBLE
    }
}