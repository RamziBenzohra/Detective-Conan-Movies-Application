package com.rb.detectiveconan.movies.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class MoviesResult <T> (
    val isLoading: Boolean = false,
    val data: Flow <T> = flow {  }
        )

