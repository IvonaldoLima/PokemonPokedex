package com.example.pokedex.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.repository.PokemonRepository
import com.example.pokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.launch

@ActivityScoped
class PokemonDetailsActivityViewModel @ViewModelInject constructor(
        private val repository: PokemonRepository
    ): ViewModel() {


    private val _pokemonLiveData = MutableLiveData<Resource<Pokemon>>()
    val pokemonLiveData: LiveData<Resource<Pokemon>>
        get() = _pokemonLiveData

    fun getPokemonDetails(id: Int){
        viewModelScope.launch{
            _pokemonLiveData.value = repository.getPokemonDetails(id)
        }
    }
}