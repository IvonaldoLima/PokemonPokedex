package com.example.pokedex.ui.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.model.PokemonResourcePaged
import com.example.pokedex.data.repository.PokemonRepository
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

@ActivityScoped
class MainActivityViewModel  @ViewModelInject constructor(private val repository: PokemonRepository): ViewModel() {

    val pokemonPagination: MutableLiveData<PokemonResourcePaged> by lazy {
        MutableLiveData<PokemonResourcePaged>()
    }

    fun testViewModel(){
        Log.d("IPL", "Logando no view model")

        viewModelScope.async(Dispatchers.IO) {
           var pokemonResource = repository.getPokemonResourcePaged()
           //var pokemon = repository.savePokemon()

            withContext(Dispatchers.Main) {
                pokemonPagination.value = pokemonResource.body()
                   Log.d("IPL", "Pokemon : $pokemonResource")
            }
        }

    }


}