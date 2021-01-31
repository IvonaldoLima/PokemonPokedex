package com.example.pokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonDTO(@PrimaryKey(autoGenerate = true)
                      var id: Long = 0,
                      val name: String)