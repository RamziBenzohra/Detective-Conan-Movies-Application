package com.rb.detectiveconan.movies.domain

import com.rb.detectiveconan.movies.data.entities.SlidesEntity
import com.rb.detectiveconan.movies.data.repository.MovieRepository
import com.rb.detectiveconan.movies.utils.Exception
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.jvm.Throws

class GetAllSlidesUseCases  @Inject constructor(
    private val movieRepository: MovieRepository,

    ) {

    @Throws
    suspend operator fun invoke(): Flow<List<SlidesEntity>> {
        val slides = movieRepository.getSlidesFromApi()
        return if (slides.isNotEmpty()){
            val isNotesAdded=movieRepository.addSlidesToDatabase(slides.map { SlidesEntity(it.thumbnail,it.title,it.streaminglink,it.id) })
            if (isNotesAdded){
                movieRepository.getSlidesFromDatabase()
            }else{
                throw Exception.MovieException("Failed to save slides to database")
            }
        }else{
            movieRepository.getSlidesFromDatabase()
        }
    }
}