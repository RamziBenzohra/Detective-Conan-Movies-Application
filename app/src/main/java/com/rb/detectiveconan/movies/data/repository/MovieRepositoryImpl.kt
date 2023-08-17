package com.rb.detectiveconan.movies.data.repository

import android.util.Log
import com.rb.detectiveconan.movies.data.entities.CastEntity
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import com.rb.detectiveconan.movies.data.entities.SlidesEntity
import com.rb.detectiveconan.movies.data.local.MovieDAO
import com.rb.detectiveconan.movies.data.remote.api.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepositoryImpl
@Inject constructor(private val movieDAO: MovieDAO,
                    private val api: Api,
                    ) : MovieRepository {

    override fun getMoviesFromDatabase(searchTitle:String) = movieDAO.getAllMovies(searchTitle)

    override  fun getSlidesFromDatabase() = movieDAO.getAllSlides()

    override suspend fun getCastFromDatabase(): Flow<List<CastEntity>> {
        return try {
            movieDAO.getAllCast()
        }catch (e:Exception){
            e.printStackTrace()
            flow {  }
        }
    }

    override suspend fun getMovieByMovieIdFromDatabase(id:String): Flow<MovieEntity>{
        return try {
            movieDAO.getMovieById(id)
        }catch (e:Exception){
            e.printStackTrace()
            flow {  }
        }
    }

       override suspend fun getMoviesFromApi(): List<MovieEntity> {
        return try {
            api.getAllMovies()
        }catch (e:HttpException){
            e.printStackTrace()
            emptyList()
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getSlidesFromApi(): List<MovieEntity> {
        return try {
            api.getAllSlides()
        }catch (e:HttpException){
            e.printStackTrace()
            emptyList()
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getCastsFromApi(): List<CastEntity> {
        return try {
            api.getAllCasts()
        }catch (e:HttpException){
            e.printStackTrace()
            emptyList()
        }catch (e:Exception){
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun addMoviesToDatabase(movies: List<MovieEntity>): Boolean {
        return try {
            movieDAO.insertAllMovies(movies)
            true
        }catch (e:Exception){
            e.printStackTrace()
            false
        }
    }

    override suspend fun addSlidesToDatabase(slides: List<SlidesEntity>): Boolean {
        return try {
            movieDAO.insertAllSlides(slides)
            true
        }catch (e:Exception){
            e.printStackTrace()
            false
        }
    }

    override suspend fun addCastesToDatabase(casts: List<CastEntity>): Boolean {
        return try {
            movieDAO.insertAllCastes(casts)
            true
        }catch (e:Exception){
            e.printStackTrace()
            false
        }
    }

    override suspend fun deleteAllSlides(): Int {
        return try {
            movieDAO.deleteAllSlides()
        }catch (e:Exception){
            e.printStackTrace()
            0
        }
    }
}