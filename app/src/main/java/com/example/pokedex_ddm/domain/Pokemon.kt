package com.example.pokedex_ddm.domain

import java.io.Serializable

data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<PokemonType>
) : Serializable {
    val formattedNumber: String
        get() = String.format("%03d", id)

    val formattedName: String
        get() = name.capitalize()

    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}

