package com.example.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.Constants
import com.example.pokedex.data.model.PokemonDTO
import com.example.pokedex.databinding.ActivityMainBinding
import com.example.pokedex.ui.adapter.PokemonAdapter
import com.example.pokedex.ui.adapter.PokemonLoadStateAdapter
import com.example.pokedex.ui.viewmodel.MainActivityViewModel
import com.example.pokedex.util.DateFormatter
import com.example.pokedex.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()
    @Inject lateinit var dateFormatter: DateFormatter

    private val adapterPokemon: PokemonAdapter by lazy {
        PokemonAdapter(
                applicationContext
        ) { onClickRecyclerView(it) }
    }

    private fun onClickRecyclerView(value: PokemonDTO){
        val intent = Intent(this, PokemonDetailsActivity::class.java).apply {
            putExtra(Constants.EXTRA_POKEMON, value.idPokemon);
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        setupObservers();

        lifecycleScope.launch{
            viewModel.pokemonsPager.collectLatest {pagingData ->
                adapterPokemon.submitData(pagingData)
            }
        }
        lifecycleScope.launch{
            adapterPokemon.loadStateFlow.collectLatest { loadStates ->
                var progressBar = loadStates.refresh is LoadState.Loading
                Log.d("IPL", "progressBar: $progressBar")
                var isVisible = loadStates.refresh !is LoadState.Loading
                Log.d("IPL", "isVisible: $isVisible")
                var errorMsg = loadStates.refresh is LoadState.Error
                Log.d("IPL", "errorMsg: $errorMsg$loadStates")
            }
        }
    }

    private fun setupObservers(){
        viewModel.pokemons.observe(this, Observer {
            when (it.status){
                Resource.Status.SUCCESS -> {
                  //  if (it.data != null) pokemonAdapter.addAll(it.data)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(applicationContext, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    Toast.makeText(applicationContext, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setupAdapter(){
        val layoutManager = GridLayoutManager(applicationContext, 1)
        binding.pokemonRecyclerView.adapter = adapterPokemon
        binding.pokemonRecyclerView.layoutManager = layoutManager
        binding.pokemonRecyclerView.addItemDecoration(PokemonAdapter.ItemDecoration(20))
        binding.pokemonRecyclerView.adapter = adapterPokemon.withLoadStateFooter(
                    footer = PokemonLoadStateAdapter { adapterPokemon.retry() }
        )
    }
}