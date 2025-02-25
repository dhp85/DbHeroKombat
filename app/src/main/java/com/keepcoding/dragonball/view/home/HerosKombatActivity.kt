package com.keepcoding.dragonball.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.keepcoding.dragonball.databinding.ActivityHerosKombatBinding
import kotlinx.coroutines.launch

class HerosKombatActivity : AppCompatActivity() {

    companion object {

        private val TAG_TOKEN = "token"
        fun startJuegoActivity(context: Context, token: String) {
            val intent = Intent(context, HerosKombatActivity::class.java)
            intent.putExtra(TAG_TOKEN, token)
            context.startActivity(intent)
        }
    }

    private val viewModel: HerosKombatViewModel by viewModels()
    private lateinit var binding: ActivityHerosKombatBinding
    private  val herosAdapter = HeroAdapter(
        onHeroCliked = { HeroModel ->
            Toast.makeText(this, HeroModel.name, Toast.LENGTH_SHORT).show()
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHerosKombatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra(TAG_TOKEN)
        token?.let {
            viewModel.updateToken(token)
        } ?: run {
            Toast.makeText(this,"No hay token la activity se va a cerrar", Toast.LENGTH_LONG)
            finish()
        }

        initViews()
        setObservers()
        viewModel.loadHeros()
    }

    private fun initViews() {
        binding.Rclistheros.layoutManager = LinearLayoutManager(this)
        binding.Rclistheros.adapter = herosAdapter
    }

    private fun setObservers(){

        lifecycleScope.launch {
            viewModel.uiState.collect { state->
                when(state){

                    is HerosKombatViewModel.State.Loading -> {
                    loadingSettings()
                    }
                    is HerosKombatViewModel.State.Success -> {
                        successSettings()
                        herosAdapter.updateHeros(state.Heros)
                    }
                    is HerosKombatViewModel.State.Error -> {
                        errorSettings()
                    }
                }

            }
        }

    }

// FUNTIONS SETTINGS VIEWS
    private fun loadingSettings() {
        binding.loadingheroslist.visibility = View.VISIBLE
        binding.Rclistheros.visibility = View.GONE
    }

    private fun successSettings() {
        binding.loadingheroslist.visibility = View.GONE
        binding.Rclistheros.visibility = View.VISIBLE
    }

    private fun errorSettings(){
        binding.loadingheroslist.visibility = View.GONE
    }
}