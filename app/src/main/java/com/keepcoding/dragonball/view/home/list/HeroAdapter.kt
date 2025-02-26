package com.keepcoding.dragonball.view.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.keepcoding.dragonball.R
import com.keepcoding.dragonball.model.HeroModel
import com.keepcoding.dragonball.databinding.ItemHerosKombatBinding

class HeroAdapter(private var onHeroCliked: (HeroModel) -> Unit) :
    RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {

    private var heros = listOf<HeroModel>()

    fun updateHeros(heros: List<HeroModel>) {
        this.heros = heros
        notifyDataSetChanged()
    }

    class HeroViewHolder(
        private val binding: ItemHerosKombatBinding,
        private var onHeroCliked: (HeroModel) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hero: HeroModel) {
            binding.heroName.text = hero.name
            Glide
                .with(binding.root)
                .load(hero.photo)
                .centerInside()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.photo)
            binding.root.setOnClickListener {
                onHeroCliked(hero)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {

        return HeroViewHolder(
            binding = ItemHerosKombatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onHeroCliked = onHeroCliked
        )
    }

    override fun getItemCount(): Int {
        return heros.size
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(heros[position])
    }

}