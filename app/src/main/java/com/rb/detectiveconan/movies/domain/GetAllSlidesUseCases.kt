package com.rb.detectiveconan.movies.domain

import android.util.Log
import com.rb.detectiveconan.movies.data.entities.SlidesEntity
import com.rb.detectiveconan.movies.data.repository.MovieRepository
import com.rb.detectiveconan.movies.utils.Exception
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.jvm.Throws

class GetAllSlidesUseCases @Inject constructor(
    private val movieRepository: MovieRepository,

    ) {

    suspend fun catchSlidesOnDatabase() {
        val slides = movieRepository.getSlidesFromApi()
        Log.d("MAIN_FRAGMENT", "Slides from api ${slides.size}")
        if (slides.isNotEmpty()) {
            movieRepository.deleteAllSlides()
            movieRepository.addSlidesToDatabase(slides.map {
                SlidesEntity(
                    it.thumbnail,
                    it.title,
                    it.streaminglink,
                    it.id
                )
            }).apply {
                Log.d("MAIN_FRAGMENT", "Slides added to database")
            }

        } else Log.d("MAIN_FRAGMENT", "Slides Failed to load from API")
    }


    fun getAllSlides() = movieRepository.getSlidesFromDatabase()


}