package com.keepcoding.dragonball

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.dragonball.Model.HeroModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HerosKombatViewModel : ViewModel() {

    sealed class State {
        data object Loading : State()
        data class Success(val Heros: List<HeroModel>) : State()
        data class Error(val message: String) : State()
    }

    private val _uiState = MutableStateFlow<State>(State.Loading)
    val uiState: StateFlow<State> = _uiState.asStateFlow()


    fun loadHeros() {
        viewModelScope.launch {
            _uiState.value = State.Loading
            delay(2000)
            val heros = fetchHeros()
            _uiState.value = State.Success(heros)
        }
    }

    private fun fetchHeros() = listOf(
        HeroModel(
            id = "1",
            name = "Goku",
            photo = "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/goku1.jpg?width=300",
            currentLife = 100,
            maxLife = 100
        ),
        HeroModel(
            id = "2",
            name = "Vegeta",
            photo = "https://cdn.alfabetajuega.com/alfabetajuega/2020/12/vegetita.jpg?width=300",
            currentLife = 100,
            maxLife = 100
        ),
        HeroModel(
            id = "3",
            name = "Bulma",
            photo = "https://cdn.alfabetajuega.com/alfabetajuega/2021/01/Bulma-Dragon-Ball.jpg?width=300",
            currentLife = 100,
            maxLife = 100
        ),
        HeroModel(
            id = "4",
            name = "Celula",
            photo = "https://cdn.alfabetajuega.com/alfabetajuega/2020/05/3CD8B1C5-134E-419E-AB5D-D1440C922A7E-e1590480274537.png?width=300",
            currentLife = 100,
            maxLife = 100
        )
    )

}