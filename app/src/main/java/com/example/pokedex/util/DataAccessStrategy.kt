package com.example.pokedex.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

fun <T, A, C> performGetOperation(databaseQuery: () -> LiveData<T>,
                               networkCall: suspend () -> Resource<A>,
                               mapData: suspend (Resource<A>) -> List<C>,
                               saveCallResult: suspend (List<C>) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map {
            Resource.success(it)
        }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            var mappedData = mapData(responseStatus)
            saveCallResult(mappedData)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }
    }