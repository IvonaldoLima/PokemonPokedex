package com.example.pokedex.data.model

import com.google.gson.annotations.SerializedName

data class PokemonResourcePaged(
        @SerializedName("count") val count: Int,
        @SerializedName("next") val next: String,
        @SerializedName("previous") val previous: String?,
        @SerializedName("results") val results: List<PokemonResource>)

data class PokemonResource(@SerializedName("name") val name: String,
                           @SerializedName("url") val url: String)

data class PokemonNetwork(@SerializedName("id") val id: Int,
                          @SerializedName("name") val name: String,
                          @SerializedName("height") val height: Int,
                          @SerializedName("weight") val weight: Int,
                          @SerializedName("stats") val stats: List<BaseStat>,
                          @SerializedName("types") val types: List<BaseType>)

data class BaseStat(@SerializedName("base_stat") val baseStat: Int,
                    @SerializedName("effort") val effort: String,
                    @SerializedName("stat") val stat: Stat)

data class Stat(@SerializedName("name") val name: String)

data class BaseType(@SerializedName("slot") val slot: Int,
                    @SerializedName("type") val type: Type)

data class Type(@SerializedName("name") val name: String)

data class PokemonSprites(var front_default: String)

