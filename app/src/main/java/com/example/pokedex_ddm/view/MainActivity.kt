package com.example.pokedex_ddm.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex_ddm.R
import com.example.pokedex_ddm.databinding.ActivityMainBinding
import com.example.pokedex_ddm.domain.Pokemon
import com.example.pokedex_ddm.viewmodel.PokemonViewModel
import com.example.pokedex_ddm.viewmodel.PokemonViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PokemonAdapter
    private var showFavoritesOnly = false

    private val viewModel by lazy {
        ViewModelProvider(this, PokemonViewModelFactory()).get(PokemonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.rvPokemon)
        adapter = PokemonAdapter(emptyList()) { pokemon ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("POKEMON_DETAIL", pokemon)
            startActivity(intent)
        }

        recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
        recyclerView.adapter = adapter

        // Observar o estado de carregamento do ViewModel
        viewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvPokemon.visibility = View.GONE
                binding.btnFilterFavorites.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvPokemon.visibility = View.VISIBLE
                binding.btnFilterFavorites.visibility = View.VISIBLE
            }
        })

        viewModel.pokemons.observe(this, Observer {
            adapter.updateData(it)
        })
    }

    fun filterFavorites(view: View) {
        showFavoritesOnly = !showFavoritesOnly
        if (showFavoritesOnly) {
            adapter.filterFavorites()
            (view as Button).text = "Mostrar todos"
        } else {
            adapter.showAllPokemons()
            (view as Button).text = "Mostrar favoritos"
        }
    }

}
