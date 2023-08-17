package com.rb.detectiveconan.movies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.ABORT
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.rb.detectiveconan.movies.data.entities.CastEntity
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import com.rb.detectiveconan.movies.data.entities.SlidesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {
    @Query("SELECT * FROM MovieEntity WHERE title LIKE '%' || :searchTitle || '%' ")
     fun getAllMovies(searchTitle:String):Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
     fun  getMovieById(id:String):Flow<MovieEntity>

    @Query("SELECT * FROM SlidesEntity")
    fun getAllSlides():Flow<List<SlidesEntity>>

    @Query("DELETE FROM SlidesEntity")
    suspend fun deleteAllSlides():Int

    @Query("SELECT * FROM SlidesEntity WHERE id = :id")
    fun  getSlideById(id:String):Flow<SlidesEntity>

    @Query("SELECT * FROM CastEntity")
    fun getAllCast():Flow<List<CastEntity>>

    @Query("SELECT * FROM CastEntity WHERE id = :id")
    fun  getCastById(id:String):Flow<CastEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertAllMovies(list: List<MovieEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllSlides(list: List<SlidesEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllCastes(list: List<CastEntity>)

}