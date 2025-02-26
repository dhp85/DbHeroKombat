package com.keepcoding.dragonball.view.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.keepcoding.dragonball.databinding.FragmentDetailBinding
import com.keepcoding.dragonball.view.home.HerosKombatViewModel
import kotlinx.coroutines.launch

class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: HerosKombatViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater,container, false)
        return binding.root
    }

    private fun setObservers(){

        lifecycleScope.launch {
            viewModel.uiState.collect { state->
                when(state){
                    is HerosKombatViewModel.State.HeroSelected -> {
                        binding.tvFDetail.text = state.hero.name
                        binding.pbFDetail.progress = 50
                    }
                    else -> Unit
                }

            }
        }

    }

}