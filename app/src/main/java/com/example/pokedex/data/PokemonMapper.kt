package com.example.pokedex.data

import com.example.pokedex.data.model.Pokemon
import com.example.pokedex.data.model.PokemonNetwork
import java.util.*
import javax.inject.Inject

class PokemonMapper @Inject constructor() {
    fun pokemonDetailMap(pokemonNetwork: PokemonNetwork): Pokemon {

        val hp = pokemonNetwork.stats.find { it.stat.name.toLowerCase(Locale.ROOT) == "hp"}
        val attack = pokemonNetwork.stats.find { it.stat.name.toLowerCase(Locale.ROOT) == "attack"}
        val defense = pokemonNetwork.stats.find { it.stat.name.toLowerCase(Locale.ROOT) == "defense"}
        val specialAttack = pokemonNetwork.stats.find { it.stat.name.toLowerCase(Locale.ROOT) == "special-attack"}
        val specialDefense = pokemonNetwork.stats.find { it.stat.name.toLowerCase(Locale.ROOT) == "special-defense"}
        val speed = pokemonNetwork.stats.find { it.stat.name.toLowerCase(Locale.ROOT) == "speed"}
        val types = pokemonNetwork.types.map { it.type.name }.toList()

        return Pokemon( id = pokemonNetwork.id,
                        name = pokemonNetwork.name.capitalize(Locale.ROOT),
                        height = pokemonNetwork.height,
                        weight = pokemonNetwork.weight,
                        hp = hp?.baseStat ?: 0,
                        attack = attack?.baseStat ?: 0,
                        defense = defense?.baseStat ?: 0,
                        especialAttack = specialAttack?.baseStat ?: 0,
                        especialDefense = specialDefense?.baseStat ?: 0,
                        speed = speed?.baseStat ?: 0,
                        types = types
                )
    }
}