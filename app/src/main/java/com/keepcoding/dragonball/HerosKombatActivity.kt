package com.keepcoding.dragonball

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.keepcoding.dragonball.databinding.ActivityHerosKombatBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HerosKombatActivity : AppCompatActivity() {

    private val viewModel: HerosKombatViewModel by viewModels()
    private lateinit var binding: ActivityHerosKombatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHerosKombatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()

    }

    private fun setObservers(){

        lifecycleScope.launch {
            viewModel.uiState.collect { state->
                when(state){

                    is HerosKombatViewModel.State.Loading -> {

                    }
                    is HerosKombatViewModel.State.Success -> {

                    }
                    is HerosKombatViewModel.State.Error -> {

                    }
                }

            }
        }

    }
}