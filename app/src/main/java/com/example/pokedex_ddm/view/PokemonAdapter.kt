package com.example.pokedex_ddm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex_ddm.R
import com.example.pokedex_ddm.domain.Pokemon

class PokemonAdapter(private var pokemons: List<Pokemon?>, private val onItemClick: (Pokemon) -> Unit) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    private var showFavoritesOnly = false
    private lateinit var originalPokemons: List<Pokemon?>

    // Método para atualizar os dados do adapter
    fun updateData(newPokemons: List<Pokemon?>) {
        pokemons = newPokemons
        originalPokemons = newPokemons
        notifyDataSetChanged()
    }

    fun filterFavorites() {
        pokemons = pokemons.filter { it?.isFavorite == true }
        notifyDataSetChanged()
    }

    fun showAllPokemons() {
        pokemons = originalPokemons
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = pokemons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(pokemons[position])

        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fade_in)
        holder.itemView.startAnimation(animation)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Pokemon?) = with(itemView) {
            val ivPokemon = findViewById<ImageView>(R.id.ivPokemon)
            val tvNumber = findViewById<TextView>(R.id.tvNumber)
            val tvName = findViewById<TextView>(R.id.tvName)
            val tvType1 = findViewById<TextView>(R.id.tvType1)
            val tvType2 = findViewById<TextView>(R.id.tvType2)
            val btnFavorite = findViewById<Button>(R.id.btnFavorite)

            item?.let {
                Glide.with(itemView.context).load(it.imageUrl).into(ivPokemon)

                tvNumber.text = "N° ${item.formattedNumber}"
                tvName.text = item.formattedName
                tvType1.text = item.types[0].name.capitalize()

                if (item.types.size > 1) {
                    tvType2.visibility = View.VISIBLE
                    tvType2.text = item.types[1].name.capitalize()
                } else {
                    tvType2.visibility = View.GONE
                }

                btnFavorite.text = if (item.isFavorite) "Desfavoritar" else "Favoritar"

                itemView.setOnClickListener {
                    onItemClick(item)
                }

                btnFavorite.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        item?.isFavorite = !item.isFavorite
                        btnFavorite.text = if (item.isFavorite) "Desfavoritar" else "Favoritar"
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }
}
