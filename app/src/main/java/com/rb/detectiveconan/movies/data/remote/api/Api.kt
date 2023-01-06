package com.rb.detectiveconan.movies.data.remote.api

import com.rb.detectiveconan.movies.data.entities.CastEntity
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("movies")
    suspend fun getAllMovies(

    ):List<MovieEntity>

    @GET("slides")
    suspend fun getAllSlides(

    ):List<MovieEntity>

    @GET("casting")
    suspend fun getAllCasts(

    ):List<CastEntity>

}