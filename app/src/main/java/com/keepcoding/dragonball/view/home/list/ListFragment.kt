package com.keepcoding.dragonball.view.home.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.keepcoding.dragonball.databinding.FragmentListBinding
import com.keepcoding.dragonball.view.home.HeroAdapter
import com.keepcoding.dragonball.view.home.HerosKombatViewModel
import kotlinx.coroutines.launch

class ListFragment: Fragment() {

    private val viewModel: HerosKombatViewModel by activityViewModels()

    private  val herosAdapter = HeroAdapter(
        onHeroCliked = { HeroModel ->
            Toast.makeText(this.context, HeroModel.name, Toast.LENGTH_SHORT).show()
        }
    )

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater,container, false )
        initViews()
        setObservers()
        return binding.root
    }

    private fun initViews() {
        binding.Rclistheros.layoutManager = LinearLayoutManager(this.context)
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