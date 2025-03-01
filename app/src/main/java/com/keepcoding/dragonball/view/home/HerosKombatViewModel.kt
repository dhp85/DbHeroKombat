package com.keepcoding.dragonball.view.home

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keepcoding.dragonball.model.HeroModel
import com.keepcoding.dragonball.repository.HerosRepository
import com.keepcoding.dragonball.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HerosKombatViewModel : ViewModel() {

    sealed class State {
        data object Loading : State()
        data class Success(val Heros: List<HeroModel>) : State()
        data class Error(val message: String) : State()
        data class HeroSelected(val hero: HeroModel): State()
    }

    private val _uiState = MutableStateFlow<State>(State.Loading)
    private val herosRepository = HerosRepository()
    private val userRepository = UserRepository()

    val uiState: StateFlow<State> = _uiState.asStateFlow()


    fun loadHeros() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = State.Loading
            val listOfHeros = herosRepository.fetchHeros(userRepository.getToken())
            when(listOfHeros) {
                is HerosRepository.HerosResponse.Success -> {
                    _uiState.value = State.Success(listOfHeros.heros)
                }
                is  HerosRepository.HerosResponse.Error -> {
                    _uiState.value = State.Error(listOfHeros.message)
                }
            }
        }
    }

    fun fightHero(hero: HeroModel) {
        if (hero.currentLife > 0) {
            hero.currentLife = (hero.currentLife - 60).coerceAtLeast(0)
        }
    }

    fun healHero(hero: HeroModel) {
        if (hero.currentLife < 100) {
            hero.currentLife += 20
        }
    }

    fun heroSelected(hero: HeroModel){
        hero.timesSelected++
        _uiState.value = State.HeroSelected(hero)
    }

    fun deselectedHero() {

            val listOfHeros = herosRepository.fetchHeros(userRepository.getToken())
            when(listOfHeros) {
                is HerosRepository.HerosResponse.Success -> {
                    _uiState.value = State.Success(listOfHeros.heros)
                }
                is  HerosRepository.HerosResponse.Error -> {
                    _uiState.value = State.Error(listOfHeros.message)
                }
            }
        }

    }