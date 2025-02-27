package com.keepcoding.dragonball.view.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.dragonball.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random


class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow<State>(State.Idle)
    private val userRepository = UserRepository()
    val uiState: StateFlow<State> = _uiState

    sealed class State {
        data object Idle: State()
        data object Loading: State()
        data object Successs: State()
        data class Error(val message: String, val errorCode: Int) : State()
    }

    fun login(user: String, password: String) {
        _uiState.value = State.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val loginResponse = userRepository.login(user,password)
            when(loginResponse) {
               is UserRepository.LoginResponse.Success -> {
                   _uiState.value = State.Successs
               }
                is UserRepository.LoginResponse.Error -> {
                    _uiState.value = State.Error("Error login", 401)
                }
            }
        }
    }
    /**
     * Guarda las credenciales del usuario en SharedPreferences de manera asíncrona.
     *
     * @param preferences Instancia de SharedPreferences donde se guardarán los datos.
     * @param user Nombre de usuario que se almacenará.
     * @param password Contraseña que se almacenará.
     */
    fun saveCredentials(preferences: SharedPreferences?, user: String, password: String) {
        // Ejecutamos la operación en un hilo de fondo para no bloquear la UI
        viewModelScope.launch(Dispatchers.IO) {
            // Verificamos que preferences no sea nulo antes de operar sobre él
            preferences?.edit()?.apply {
                putString("User", user) // Guardamos el nombre de usuario
                putString("Password", password) // Guardamos la contraseña
                apply() // Aplicamos los cambios de forma asíncrona
            }
        }
    }
}

// Si el usuario ya hecho login, no volverselo a pedir.
// Si el usuario le ha dado a recordar ususario y contraseña. Despues mostralo en el login.