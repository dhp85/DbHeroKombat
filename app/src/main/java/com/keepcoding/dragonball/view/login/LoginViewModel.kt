package com.keepcoding.dragonball.view.login

import android.content.SharedPreferences
import android.util.Log
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
    fun saveCredentials(preferences: SharedPreferences?, user: String, password: String, rememberMe: Boolean) {
        // Ejecutamos la operación en un hilo de fondo para no bloquear la UI
        viewModelScope.launch(Dispatchers.IO) {
            // Verificamos que preferences no sea nulo antes de operar sobre él
            preferences?.edit()?.apply {
                putString("User", user) // Guardamos el nombre de usuario
                putString("Password", password) // Guardamos la contraseña
                putBoolean("RememberMe", true)
                apply() // Aplicamos los cambios de forma asíncrona
            }
        }
        Log.d("LoginViewModel", "Credenciales guardadas: Usuario=$user, Contraseña=$password, RememberMe=$rememberMe")
    }

    /**
     * Recupera las credenciales del usuario de SharedPreferences.
     *
     * @param preferences Instancia de SharedPreferences desde donde se leerán los datos.
     * @return Un par con el nombre de usuario y la contraseña, o null si no existen.
     */
    fun getCredentials(preferences: SharedPreferences?): Pair<String?, String?> {
        val user = preferences?.getString("User", null)
        val password = preferences?.getString("Password", null)
        return Pair(user, password)
    }

    fun getRememberMeState(preferences: SharedPreferences?): Boolean {
        return preferences?.getBoolean("RememberMe", false) ?: false
    }

    /**
     * Elimina las credenciales guardadas de SharedPreferences.
     *
     * @param preferences Instancia de SharedPreferences desde donde se eliminarán los datos.
     */
    fun clearCredentials(preferences: SharedPreferences?) {
        preferences?.edit()?.apply {
            remove("User")
            remove("Password")
            remove("RememberMe")
            apply()
        }
    }
}
