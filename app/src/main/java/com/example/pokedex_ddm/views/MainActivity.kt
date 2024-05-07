package com.example.pokedex_ddm.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex_ddm.R
import com.example.pokedex_ddm.databinding.ActivityMainBinding
import com.example.pokedex_ddm.domain.Pokemon
import com.example.pokedex_ddm.domain.PokemonType

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView =findViewById<RecyclerView>(R.id.rvPokemon)

        val charmander = Pokemon(
            "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/004.png",
            4,
            "Charmander",
            listOf(
                PokemonType("Fire")
            )
        )
        val pokemons = listOf(
            charmander, charmander, charmander, charmander, charmander
        )

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = PokemonAdapter(pokemons)
    }
}