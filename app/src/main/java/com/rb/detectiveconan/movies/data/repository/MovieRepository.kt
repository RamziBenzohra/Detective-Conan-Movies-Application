package com.rb.detectiveconan.movies.data.repository

import com.rb.detectiveconan.movies.data.entities.CastEntity
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import com.rb.detectiveconan.movies.data.entities.SlidesEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository{
    suspend fun getMoviesFromDatabase():Flow<List<MovieEntity>>
    suspend fun getSlidesFromDatabase():Flow<List<SlidesEntity>>
    suspend fun getCastFromDatabase():Flow<List<CastEntity>>
    suspend fun getMovieByMovieIdFromDatabase(id:String):Flow<MovieEntity>
    suspend fun getMoviesFromApi():List<MovieEntity>
    suspend fun getSlidesFromApi():List<MovieEntity>
    suspend fun getCastsFromApi():List<CastEntity>
    suspend fun addMoviesToDatabase(movies:List<MovieEntity>):Boolean
    suspend fun addSlidesToDatabase(slides:List<SlidesEntity>):Boolean
    suspend fun addCastesToDatabase(casts:List<CastEntity>):Boolean
}