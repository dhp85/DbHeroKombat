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
import com.keepcoding.dragonball.view.home.detail.DetailFragment
import com.keepcoding.dragonball.view.home.list.ListFragment
import kotlinx.coroutines.launch
import kotlin.random.Random


interface HerosKombatProtocol {
    fun navListFragments()
    fun navDetailFragments()
}

class HerosKombatActivity : AppCompatActivity(), HerosKombatProtocol {

    companion object {


        fun startJuegoActivity(context: Context) {
            val intent = Intent(context, HerosKombatActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val viewModel: HerosKombatViewModel by viewModels()
    private lateinit var binding: ActivityHerosKombatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHerosKombatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loadHeros()
        navListFragments()
    }

    override fun navListFragments() {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.flList.id, ListFragment())
            commit()
        }
    }

    override fun navDetailFragments() {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.flList.id, DetailFragment())
            addToBackStack(null)
            commit()
        }
    }
}