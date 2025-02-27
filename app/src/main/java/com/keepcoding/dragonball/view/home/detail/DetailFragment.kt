package com.keepcoding.dragonball.view.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.keepcoding.dragonball.databinding.FragmentDetailBinding
import com.keepcoding.dragonball.model.HeroModel
import com.keepcoding.dragonball.view.home.HerosKombatViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: HerosKombatViewModel by activityViewModels()
    private  var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater,container, false)
        setObservers()
        return binding.root
    }

    private fun initViews (hero: HeroModel) {
        with(binding) {
            tvFDetail.text = hero.name
            pbFDetail.progress = hero.currentLife
            buttonFight.setOnClickListener {
                viewModel.fightHero(hero)
                binding.pbFDetail.progress = hero.currentLife
            }
        }
    }

    private fun setObservers(){

        job = lifecycleScope.launch {
            viewModel.uiState.collect { state->
                when(state){
                    is HerosKombatViewModel.State.HeroSelected -> {
                        initViews(state.hero)
                    }
                    else -> Unit
                }

            }
        }

    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
        viewModel.deselectedHero()
    }

}