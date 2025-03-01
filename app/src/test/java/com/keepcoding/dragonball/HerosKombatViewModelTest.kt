package com.keepcoding.dragonball

import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.keepcoding.dragonball.model.HeroModel
import com.keepcoding.dragonball.repository.HerosRepository
import com.keepcoding.dragonball.view.home.HerosKombatViewModel
import com.keepcoding.dragonball.view.home.HerosKombatViewModel.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HerosKombatViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val vm = HerosKombatViewModel()

    private val hero = HeroModel("1", "Goku", "http", 100, 100, 0)
    private val heroSmallLife = HeroModel("2", "Vegeta", "http", 50, 100, 0)
    private val deadHero = HeroModel("3", "Celula", "http", 0, 100, 0)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `fightHero down life`() {

        val heroExpectation = hero.copy(
            currentLife = 90
        )
        vm.fightHero(hero)
        assertEquals(heroExpectation, hero)
    }

    @Test
    fun `fightHero when currentLife = 0,does not go below zero`() {

        val heroExpectation = deadHero.copy(
            currentLife = 0
        )
        vm.fightHero(deadHero)
        assertEquals(heroExpectation, deadHero)
    }

    @Test
    fun `healHero up life`() {

        val heroExpectation = heroSmallLife.copy(
            currentLife = 70
        )
        vm.healHero(heroSmallLife)
        assertEquals(heroExpectation, heroSmallLife)
    }

    @Test
    fun `healHero life, it doesn't go up more than 100`() {

        val heroExpectation = hero.copy(
            currentLife = 100
        )
        vm.healHero(hero)
        assertEquals(heroExpectation, hero)
    }


    @Test
    fun `when hero selected, state update HeroSelected with hero`() = runTest {
                        vm.uiState.test {
                            assertEquals(State.Loading, awaitItem())
                            vm.heroSelected(hero)
                            assertEquals(State.HeroSelected(hero), awaitItem())
                            cancelAndIgnoreRemainingEvents()
                        }
    }

    @Test
    fun `when hero deselected, state update HeroSelected Success`() = runTest {
        vm.uiState.test {
            vm.userRepository.setToken("eyJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJpZGVudGlmeSI6IkZCRjlCRTNGLTZDODAtNDYxQi05QUUwLUMwNTE5QjQ4RjFGNyIsImVtYWlsIjoiZGllZ29ocDg1QGdtYWlsLmNvbSIsImV4cGlyYXRpb24iOjY0MDkyMjExMjAwfQ.xLt3qp7w569qDiJdUsaRUmg0uzHtcjetfxYWxl8iLGg")
            assertEquals(State.Loading, awaitItem())
            vm.deselectedHero()
            assertTrue(awaitItem() is State.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when loadHeros, state update Success`() = runTest {
        vm.uiState.test {
            vm.userRepository.setToken("eyJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJpZGVudGlmeSI6IkZCRjlCRTNGLTZDODAtNDYxQi05QUUwLUMwNTE5QjQ4RjFGNyIsImVtYWlsIjoiZGllZ29ocDg1QGdtYWlsLmNvbSIsImV4cGlyYXRpb24iOjY0MDkyMjExMjAwfQ.xLt3qp7w569qDiJdUsaRUmg0uzHtcjetfxYWxl8iLGg")
            assertEquals(State.Loading, awaitItem())
            vm.loadHeros()
            assertTrue(awaitItem() is State.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }


}
