package com.example.pokedex.data

import android.net.UrlQuerySanitizer
import android.util.Log
import androidx.paging.PagingSource
import com.example.pokedex.data.local.PokemonDao
import com.example.pokedex.data.model.PokemonDTO
import com.example.pokedex.data.model.PokemonResourcePaged
import com.example.pokedex.data.remote.PokemonRemoteDataSource
import com.example.pokedex.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PokemonPagingSource @Inject constructor(
        private val pokemonRemoteDataSource: PokemonRemoteDataSource,
        private val providePokemonDao: PokemonDao
): PagingSource<Int, PokemonDTO>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonDTO> {
        try {
            val limit = 20
            var nextPage = params.key ?: 0
            var previousPage: Int? = null

            var pokemonsLocalSource = providePokemonDao.getAllPaged(offset = nextPage, limit = limit)
            Log.d("IPL2", "NextPage $nextPage")

            if (pokemonsLocalSource.isNotEmpty()){
                Log.d("IPL2", "Retorno do banco: $nextPage")
                var next = if (pokemonsLocalSource.isEmpty()) null else nextPage + limit
                return  result(data = pokemonsLocalSource, prevKey = previousPage, nextKey = next)
            }

            val response = pokemonRemoteDataSource.getPokemonsPaged(nextPage, limit)

            var pokemonsRemoteSource = listOf<PokemonDTO>()

            if (response.status == Resource.Status.SUCCESS) {
               pokemonsRemoteSource = mapPokemonToDataBaseObject(response)
               providePokemonDao.insertAll(pokemonsRemoteSource)
                val nextKey = if (pokemonsRemoteSource.isEmpty()) null else nextPage + limit
                return  result(data = pokemonsRemoteSource, prevKey = previousPage, nextKey = nextKey)
            } else if (response.status == Resource.Status.ERROR) {
                return LoadResult.Error(Exception(response.message))
            }

            return  result(data = pokemonsRemoteSource, prevKey = previousPage, nextKey = null)

        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    private fun <T : Any> result(data: List<T>, prevKey: Int?, nextKey: Int?): LoadResult.Page<Int, T> {
        return LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
        )
    }

    private fun getNextPage(nextPageUrl: String): Int {
        return UrlQuerySanitizer(nextPageUrl).getValue("offset").toInt()
    }

    private fun getPreviousPage(previousPageUrl: String): Int {
        return UrlQuerySanitizer(previousPageUrl).getValue("offset").toInt()
    }

    private fun mapPokemonToDataBaseObject(
            resource: Resource<PokemonResourcePaged>
    ): List<PokemonDTO> {

        val pokemons: MutableList<PokemonDTO> = mutableListOf()

        resource.data?.results?.map {
            val idPokemon = getIdPokemon(it.url)
            val urlImagePokemon = getUrlImagePokemon(idPokemon)
            pokemons.add(PokemonDTO(name = it.name, idPokemon = idPokemon, urlImagePokemon = urlImagePokemon))
        }
        return pokemons
    }

    private fun getIdPokemon(url: String): Int{
        val value = url.split("/")
        var id = value[value.size-2]
        return id.toInt()
    }

    private fun getUrlImagePokemon(idPokemon: Int): String{
        return  "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$idPokemon.png"
    }

}