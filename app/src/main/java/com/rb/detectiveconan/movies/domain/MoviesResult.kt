package com.rb.detectiveconan.movies.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//sealed  class MoviesResult <T> (
//    val isLoading: Boolean = false,
//    val data: Flow <T>
//        )

sealed class MoviesResult<T> {
    class Loading<T>: MoviesResult<T>()
    data class Success<T>(val data:Flow<T>) : MoviesResult<T>()
    data class Error <T>(val message: String) : MoviesResult<T>()
}


