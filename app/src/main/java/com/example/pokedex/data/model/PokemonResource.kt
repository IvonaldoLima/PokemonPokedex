package com.example.pokedex.data.model

data class PokemonResourcePaged(
        var count: Int,
        var next: String,
        var previus: String?,
        var results: List<PokemonResource>)

data class PokemonResource(var name: String, var url: String)

data class Pokemonm(var id: Int, var name: String, var sprites: PokemonSprites)

data class PokemonForm(var name: String, var sprites: PokemonSprites)

data class PokemonSprites(var front_default: String)
