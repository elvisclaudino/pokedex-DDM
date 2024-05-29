package com.example.pokedex_ddm

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.pokedex_ddm.domain.Pokemon
import com.example.pokedex_ddm.view.DetailActivity
import org.junit.Rule
import org.junit.Test
class PokemonDetailsTest {
    // Regra para inicializar a atividade de detalhes
    @get:Rule
    val activityRule = ActivityScenarioRule(DetailActivity::class.java)

    @Test
    fun testPokemonDetails() {
        // Criar um Pokémon de exemplo
        val pokemon = Pokemon(
            id = 1,
            name = "Pikachu",
            types = listOf(),
        )

        // Criar uma intent com o Pokémon de exemplo
        val intent = Intent(ApplicationProvider.getApplicationContext(), DetailActivity::class.java)
        intent.putExtra("POKEMON_DETAIL", pokemon)

        // Iniciar a atividade
        activityRule.scenario.onActivity { activity ->
            activity.startActivity(intent)
        }

        // Verificar se os detalhes do Pokémon são exibidos corretamente
        onView(withId(R.id.tvNameDetails)).check(matches(withText(pokemon.name)))
        onView(withId(R.id.tvNumberDetails)).check(matches(withText("N° ${pokemon.id}")))
        onView(withId(R.id.tvType1Detail)).check(matches(withText(pokemon.types[0].name.capitalize())))
    }
}