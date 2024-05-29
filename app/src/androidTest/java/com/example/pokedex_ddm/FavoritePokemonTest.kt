package com.example.pokedex_ddm

import com.example.pokedex_ddm.domain.Pokemon
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FavoritePokemonTest {
    @Test
    fun testToggleFavorite() {
        // Crie um Pokémon de exemplo
        val pokemon = Pokemon(
            id = 25,
            name = "Pikachu",
            types = listOf(),
        )

        // Verifique se o Pokémon não é inicialmente favorito
        assertFalse(pokemon.isFavorite)

        // Favoritar o Pokémon
        pokemon.isFavorite

        // Verifique se o Pokémon agora é favorito
        assertTrue(pokemon.isFavorite)

        // Desfavoritar o Pokémon
        pokemon.isFavorite

        // Verifique se o Pokémon não é mais favorito
        assertFalse(pokemon.isFavorite)
    }
}