package com.example.pokedex.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokedex.data.PokemonPagingSource
import com.example.pokedex.data.model.PokemonDTO
import com.example.pokedex.data.model.PokemonResourcePaged
import com.example.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ActivityScoped
class MainActivityViewModel  @ViewModelInject constructor(
    repository: PokemonRepository,
    private val pokemonPagingSource: PokemonPagingSource
    ): ViewModel() {

    var pokemons = repository.getPokemonsPaged();

    val pokemonsPager: Flow<PagingData<PokemonDTO>> =
        Pager( config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {pokemonPagingSource})
            .flow.cachedIn(viewModelScope)
}