package com.example.pokedex.data.remote

import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
    private val pokemonApiService: PokemonApiService
): BaseDataSource() {
    suspend fun getPokemonsPaged(offset: Int, limit: Int) = getResult { pokemonApiService.getPokemonResourcePaged(offset, limit) }
}