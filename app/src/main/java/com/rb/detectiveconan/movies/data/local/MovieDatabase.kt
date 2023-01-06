package com.rb.detectiveconan.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rb.detectiveconan.movies.data.entities.CastEntity
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import com.rb.detectiveconan.movies.data.entities.SlidesEntity

@Database(entities = [MovieEntity::class,SlidesEntity::class,CastEntity::class], version = 1)
abstract class MovieDatabase () :RoomDatabase(){
    abstract fun movieDao(): MovieDAO
}