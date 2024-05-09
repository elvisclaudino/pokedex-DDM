package com.example.pokedex_ddm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex_ddm.R
import com.example.pokedex_ddm.api.PokemonRepository
import com.example.pokedex_ddm.databinding.ActivityMainBinding
import com.example.pokedex_ddm.domain.Pokemon
import com.example.pokedex_ddm.viewmodel.PokemonViewModel
import com.example.pokedex_ddm.viewmodel.PokemonViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.content.Intent


class MainActivity : AppCompatActivity(), PokemonAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var details: PokemonDetail

    private val viewModel by lazy {
        ViewModelProvider(this, PokemonViewModelFactory()).get(PokemonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.rvPokemon)

        viewModel.pokemons.observe(this, Observer {
            loadRecyclerView(it)
        })
    }

    private fun loadRecyclerView(pokemons: List<Pokemon?>) {
        val layoutManager = GridLayoutManager(this@MainActivity, 2)

        recyclerView.layoutManager = layoutManager
        val adapter = PokemonAdapter(pokemons)
        adapter.setOnItemClickListener(this) // Configurar o listener de clique
        recyclerView.adapter = adapter
    }

    override fun onItemClick(pokemon: Pokemon?) {
        // Lógica para lidar com o clique em um item da lista
        // Por exemplo, abrir a tela de detalhes do Pokémon
        val intent = Intent(this, details::class.java)
        //intent.putExtra("pokemon", pokemon)
        startActivity(intent)
    }

}

