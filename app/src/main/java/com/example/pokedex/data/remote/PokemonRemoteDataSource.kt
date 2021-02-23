package com.example.pokedex.data.remote

import com.example.pokedex.data.PokemonMapper
import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
    private val pokemonApiService: PokemonApiService,
    private val pokemonMapper: PokemonMapper
): BaseDataSource() {
    suspend fun getPokemonsPaged(offset: Int, limit: Int) = getResult { pokemonApiService.getPokemonResourcePaged(offset, limit) }
    suspend fun getPokemon(id: Int) = getResultAndMap(call = {pokemonApiService.getPokemon(id)}, map = {pokemonMapper.pokemonDetailMap(it)})
}