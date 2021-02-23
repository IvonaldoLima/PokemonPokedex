package com.example.pokedex.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.data.model.PokemonDTO
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getAllPokemons() : LiveData<List<PokemonDTO>>

    @Query("SELECT * FROM pokemon")
    fun getAllPokemonsFlow() : Flow<List<PokemonDTO>>

    @Query("SELECT * FROM pokemon WHERE idPokemon = :id")
    suspend fun getPokemon(id: Int): PokemonDTO

    @Query("SELECT * FROM pokemon limit :limit offset :offset")
    suspend fun getAllPaged(offset: Int, limit: Int): List<PokemonDTO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemonDTOS: List<PokemonDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: PokemonDTO) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonsPaged(pokemonDTO: List<PokemonDTO>)


}