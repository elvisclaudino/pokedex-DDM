package com.example.pokedex_ddm.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pokedex_ddm.R
import com.example.pokedex_ddm.databinding.ActivityDetailBinding
import com.example.pokedex_ddm.domain.Pokemon

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        val pokemon = intent.getSerializableExtra("POKEMON_DETAIL") as? Pokemon
        pokemon?.let { loadPokemonDetails(it) }
    }

    private fun loadPokemonDetails(pokemon: Pokemon) {
        with(binding) {
            Glide.with(this@DetailActivity).load(pokemon.imageUrl).into(ivDetails)
            tvNumberDetails.text = "N° ${pokemon.formattedNumber}"
            tvNameDetails.text = pokemon.formattedName
            tvType1Detail.text = pokemon.types[0].name.capitalize()

            if (pokemon.types.size > 1) {
                tvType2Detail.visibility = View.VISIBLE
                tvType2Detail.text = pokemon.types[1].name.capitalize()
            } else {
                tvType2Detail.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
