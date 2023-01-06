package com.rb.detectiveconan.movies.domain

import com.rb.detectiveconan.movies.data.entities.MovieEntity
import com.rb.detectiveconan.movies.data.repository.MovieRepository
import com.rb.detectiveconan.movies.utils.Exception
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.jvm.Throws

class GetAllMoviesUseCases @Inject constructor(
    private val movieRepository: MovieRepository,

) {

    @Throws
    suspend operator fun invoke(): Flow<List<MovieEntity>> {
        val movies = movieRepository.getMoviesFromApi()
        return if (movies.isNotEmpty()){
            val isNotesAdded=movieRepository.addMoviesToDatabase(movies)
            if (isNotesAdded){
                movieRepository.getMoviesFromDatabase()
            }else{
                throw Exception.MovieException("Failed to save movies to database")
            }
        }else{
            movieRepository.getMoviesFromDatabase()
        }
    }
}