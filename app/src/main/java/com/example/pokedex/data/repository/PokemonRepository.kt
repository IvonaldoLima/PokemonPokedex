package com.example.pokedex.data.repository

import androidx.lifecycle.LiveData
import com.example.pokedex.data.remote.PokemonApiService
import com.example.pokedex.data.local.PokemonDao
import com.example.pokedex.data.model.PokemonDTO
import com.example.pokedex.data.model.PokemonResourcePaged
import com.example.pokedex.data.remote.PokemonRemoteDataSource
import com.example.pokedex.util.Resource
import com.example.pokedex.util.performGetOperation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
        private val remoteDataSource: PokemonApiService,
        private val localDataSource: PokemonDao,
        private val pokemonRemoteDataSource: PokemonRemoteDataSource
){


    private suspend fun mapearPokemonParaBaseDeDados(resource: Resource<PokemonResourcePaged>): List<PokemonDTO> {

        val pokemons: MutableList<PokemonDTO> = mutableListOf()

        resource.data?.results?.map {
          //  pokemons.add(PokemonDTO(name = it.name, idPokemon = 20, urlImagePokemon = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/19.png"))
        }
        
        return pokemons
    }

    fun getAllPokemon(): LiveData<List<PokemonDTO>> {
        return localDataSource.getAllPokemons()
    }

    fun getPokemonsPaged(offset: Int = 10, limit: Int = 10) = performGetOperation(
            databaseQuery = { localDataSource.getAllPokemons() },
            networkCall = { pokemonRemoteDataSource.getPokemonsPaged(10, 10) },
            mapData = { mapearPokemonParaBaseDeDados(it) },
            saveCallResult = { localDataSource.insertPokemonsPaged(it) }
    )

    suspend fun getPokemonDetails(id: Int) = pokemonRemoteDataSource.getPokemon(id)

}






















