package com.example.pokedex_ddm.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex_ddm.R
import com.example.pokedex_ddm.api.PokemonRepository
import com.example.pokedex_ddm.api.model.PokemonResult
import com.example.pokedex_ddm.databinding.ActivityMainBinding
import com.example.pokedex_ddm.domain.Pokemon
import com.example.pokedex_ddm.domain.PokemonType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.rvPokemon)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemonsApiResult = PokemonRepository.listPokemons()

                val pokemons: List<Pokemon?> = pokemonsApiResult?.results?.map { pokemonResult ->
                    val number = pokemonResult.url
                        .removePrefix("https://pokeapi.co/api/v2/pokemon/")
                        .removeSuffix("/").toInt()

                    val pokemonApiResult = PokemonRepository.getPokemon(number)

                    pokemonApiResult?.let {
                        Pokemon(
                            pokemonApiResult.id,
                            pokemonApiResult.name,
                            pokemonApiResult.types.map { type ->
                                type.type
                            }
                        )
                    }
                } ?: emptyList()


                withContext(Dispatchers.Main) {
                    val layoutManager = GridLayoutManager(this@MainActivity, 2)

                    recyclerView.post{
                        recyclerView.layoutManager = layoutManager
                        recyclerView.adapter = PokemonAdapter(pokemons)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
