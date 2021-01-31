package com.example.pokedex.data.remote

import com.example.pokedex.data.model.PokemonResourcePaged
import com.example.pokedex.data.model.Pokemonm
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon-form/")
    suspend fun getPokemonResourcePaged(@Query("offset") offset: Int, @Query("limit") limit: Int): Response<PokemonResourcePaged>

    @GET("pokemon/nidoqueen")
    suspend fun getPokemon(): Response<Pokemonm>

}