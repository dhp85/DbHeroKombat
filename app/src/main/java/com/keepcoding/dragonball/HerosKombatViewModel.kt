package com.keepcoding.dragonball

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HerosKombatViewModel: ViewModel() {

    sealed class State {
        data object Loading: State()
        data class Success(val Heros: List<HeroModel>): State()
        data class Error(val message: String): State()
    }

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()


    fun fetchHeros() {
        viewModelScope.launch {
            _uiState.value = State.Loading
            delay(2000)
        }
    }

}