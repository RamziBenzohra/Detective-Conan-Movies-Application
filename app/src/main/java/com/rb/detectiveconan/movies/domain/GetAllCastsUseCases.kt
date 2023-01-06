package com.rb.detectiveconan.movies.domain

import com.rb.detectiveconan.movies.data.entities.CastEntity
import com.rb.detectiveconan.movies.data.repository.MovieRepository
import com.rb.detectiveconan.movies.utils.Exception
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.jvm.Throws

class GetAllCastsUseCases @Inject constructor(
    private val movieRepository: MovieRepository,
    ) {

    @Throws
    suspend operator fun invoke(): Flow<List<CastEntity>> {
        val slides = movieRepository.getCastsFromApi()
        return if (slides.isNotEmpty()){
            val isNotesAdded=movieRepository.addCastesToDatabase(slides)
            if (isNotesAdded){
                movieRepository.getCastFromDatabase()
            }else{
                throw Exception.MovieException("Failed to save casts to database")
            }
        }else{
            movieRepository.getCastFromDatabase()
        }
    }
}