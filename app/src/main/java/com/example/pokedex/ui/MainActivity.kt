package com.example.pokedex.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.ui.adapter.PokemonAdapter
import com.example.pokedex.ui.viewmodel.MainActivityViewModel
import com.example.pokedex.util.DateFormatter
import com.example.pokedex.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()
    @Inject lateinit var dateFormatter: DateFormatter

    private val pokemonAdapter: PokemonAdapter by lazy {
        PokemonAdapter(
                applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        button()
        setupAdapter()
        setupObservers();
        viewModel.pokemonPagination.observe(this, Observer {
            Toast.makeText(applicationContext, "Data formatada: ${it}", Toast.LENGTH_SHORT).show()
        })
    }
    private fun button(){
        binding.buttonMainPage.setOnClickListener {
            viewModel.getPokemonsPaged2()
        }
    }

    private fun setupObservers(){
        viewModel.pokemons.observe(this, Observer {
            when (it.status){
                Resource.Status.SUCCESS -> {
                    if (it.data != null) pokemonAdapter.addAll(it.data)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(applicationContext, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    Toast.makeText(applicationContext, "Carregando", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setupAdapter(){
        val layoutManager = GridLayoutManager(applicationContext, GridLayoutManager.VERTICAL)
        binding.pokemonRecyclerView.adapter = pokemonAdapter
        binding.pokemonRecyclerView.layoutManager = layoutManager
    }

}