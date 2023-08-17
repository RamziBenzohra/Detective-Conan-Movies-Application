package com.rb.detectiveconan.movies.domain

import android.util.Log
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import com.rb.detectiveconan.movies.data.repository.MovieRepository
import com.rb.detectiveconan.movies.utils.Exception
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.jvm.Throws

class GetAllMoviesUseCases @Inject constructor(
    private val movieRepository: MovieRepository,

) {


     suspend fun catchMoviesOnDatabase(){
        val movies = movieRepository.getMoviesFromApi()
        Log.d("MAIN_FRAGMENT","Movies from api ${movies.size}")
        if (movies.isNotEmpty()) movieRepository.addMoviesToDatabase(movies).apply {
            Log.d("MAIN_FRAGMENT","Movies added to database")
        }else{
            Log.d("MAIN_FRAGMENT","Movies Failed to load from API")
        }
    }


     fun getAllMovies(searchTitle:String) = movieRepository.getMoviesFromDatabase(searchTitle)

}