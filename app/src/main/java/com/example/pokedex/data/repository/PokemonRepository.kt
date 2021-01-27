package com.example.pokedex.data.repository

import android.util.Log
import com.example.pokedex.data.remote.PokemonApiService
import com.example.pokedex.data.local.PokemonDao
import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.model.PokemonResourcePaged
import com.example.pokedex.data.model.Pokemonm
import com.example.pokedex.data.remote.PokemonRemoteDataSource
import com.example.pokedex.util.performGetOperation
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
        private val remoteDataSource: PokemonApiService,
        private val localDataSource: PokemonDao,
        private val pokemonRemotoDataSource: PokemonRemoteDataSource
){

    suspend fun getPokemonResourcePaged(): Response<PokemonResourcePaged> {
        Log.d("IPL", "Buscando ")
        var retorno = remoteDataSource.getPokemonResourcePaged(20, 20)

        retorno.body().let {
            var result = it?.results?.get(0)
            result?.also {
                Log.d("IPL", "Buscando pokemon I")
               var resul = getPokemon(it.name)
                var poke = resul.body()
                poke?.apply {
                    Log.d("IPL", "Buscando pokemon II")
                    Log.d("IPL", "Nome do pokemon : $this.name")
                }
            }
        }

        Log.d("IPL", "retorno : $retorno")
        return retorno
    }

    suspend fun getPokemon(id: String): Response<Pokemonm> {
        return remoteDataSource.getPokemon()
    }

    suspend fun savePokemon(): Long {
        return localDataSource.insert(Pokemon(name = "Charizarde"))
    }

    fun getCharacters(id: Int) = performGetOperation(
            databaseQuery = { localDataSource.getAllPokemons() },
            networkCall = { pokemonRemotoDataSource.getPokemons(20, 30) },
            saveCallResult = { localDataSource.insertAll(listOf()) }
    )
}