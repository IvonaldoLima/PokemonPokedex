package com.example.pokedex.data.remote

import android.util.Log
import com.example.pokedex.util.Resource
import retrofit2.Response

open class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.e("IPL: BaseDataSource =>", message)
        return Resource.error("Network call has failed for a following reason: $message")
    }

    suspend fun <T, R> getResultAndMap(
            call: suspend () -> Response<T>,
            map: suspend (T) -> R
    ): Resource<R> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null)
                {
                    val resourceMapped = map(body)
                    return Resource.success(resourceMapped)
                }
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
}