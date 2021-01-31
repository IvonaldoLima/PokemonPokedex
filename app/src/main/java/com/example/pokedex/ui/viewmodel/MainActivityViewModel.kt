package com.example.pokedex.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.PokemonResourcePaged
import com.example.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ActivityScoped
class MainActivityViewModel  @ViewModelInject constructor(private val repository: PokemonRepository): ViewModel() {

    val pokemonPagination: MutableLiveData<PokemonResourcePaged> by lazy {
        MutableLiveData<PokemonResourcePaged>()
    }

    var pokemons = repository.getPokemonsPaged();

    fun getPokemonsPaged2() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertPOkemon()
        }
    }

    fun getPokemonsFlow(){

    }

}