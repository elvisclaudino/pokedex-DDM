package com.example.pokedex_ddm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex_ddm.api.PokemonRepository
import com.example.pokedex_ddm.domain.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    var pokemons = MutableLiveData<List<Pokemon?>>()
    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemonsApiResult = PokemonRepository.listPokemons()

                pokemons.postValue(pokemonsApiResult?.results?.map { pokemonResult ->
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
                } ?: emptyList())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}