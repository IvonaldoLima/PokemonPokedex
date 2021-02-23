package com.example.pokedex.data.model

data class Pokemon(
        val id: Int,
        val name: String,
        val height: Int,
        val weight: Int,
        val hp: Int,
        val attack: Int,
        val defense: Int,
        val especialAttack: Int,
        val especialDefense: Int,
        val speed: Int,
        val types: List<String>,
){
    fun getUrlImage(): String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}

