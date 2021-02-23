package com.example.pokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonDTO(@PrimaryKey
                      val idPokemon: Int,
                      val name: String,
                      val urlImagePokemon: String){

    fun getPokemonIdWithHashTag(): String {
        val length = idPokemon.toString().length;
        if (length < 4) {
            val quantityOfZeros = 4 - length
            val zeros = generateMaskWithZero(quantityOfZeros)
            return "#$zeros$idPokemon"
        }
        return "#${idPokemon}"
    }

    private fun generateMaskWithZero(quantityOfZero: Int): String {
        var count = 0;
        var mask = ""
        while (count < quantityOfZero) {
            mask += 0
            count++
        }
        return mask
    }
}