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


class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()
        enableEdgeToEdge()

        viewModel.saveCredentials(
            preferences = getSharedPreferences("LoginPreferences", MODE_PRIVATE),
            user = binding.EditTextUser.text.toString(),
            password = binding.EditTextPassword.text.toString()
        )

        binding.loginButton.setOnClickListener{
            viewModel.login(
                user = binding.EditTextUser.text.toString(),
                password = binding.EditTextPassword.text.toString()
            )
        }

    }
    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when(state){
                    is LoginViewModel.State.Idle -> {}
                    is LoginViewModel.State.Loading -> {
                       loadingSettings()
                    }
                    is LoginViewModel.State.Successs -> {
                        successSettings()
                        Toast.makeText(this@LoginActivity, "Ir a la siguiente pantalla", Toast.LENGTH_LONG).show()
                    }
                    is LoginViewModel.State.Error -> {
                        errorSettings()
                        Toast.makeText(this@LoginActivity, "Ha ocurrido un error. ${state.message} ${state.errorCode}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    // FUNTIONS SETTINGS VIEWS
    private fun loadingSettings(){
        binding.spinningLoading.visibility = View.VISIBLE
        binding.loginButton.visibility = View.GONE
    }

    private fun successSettings(){
        binding.spinningLoading.visibility = View.GONE
        binding.loginButton.visibility = View.VISIBLE
    }

    private  fun errorSettings() {
        binding.spinningLoading.visibility = View.GONE
        binding.loginButton.visibility = View.VISIBLE
    }

}