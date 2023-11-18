package com.rb.detectiveconan.movies.di


import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.rb.detectiveconan.movies.Application
import com.rb.detectiveconan.movies.data.local.MovieDAO
import com.rb.detectiveconan.movies.data.player.VideoMetaData
import com.rb.detectiveconan.movies.data.player.VideoMetaDataImpl
import com.rb.detectiveconan.movies.data.remote.api.Api

import com.rb.detectiveconan.movies.data.repository.MovieRepository
import com.rb.detectiveconan.movies.data.repository.MovieRepositoryImpl
import com.rb.detectiveconan.movies.domain.GetAllCastsUseCases
import com.rb.detectiveconan.movies.domain.GetAllMoviesUseCases
import com.rb.detectiveconan.movies.domain.GetAllSlidesUseCases

import com.rb.detectiveconan.movies.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModules {

    @Provides
    @ViewModelScoped
    fun provideApi() : Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @ViewModelScoped
    fun provideMoviesRepository(dao: MovieDAO, api: Api) : MovieRepository {
        return MovieRepositoryImpl(dao,api)
    }
    @Provides
    @ViewModelScoped
    fun provideGetAllMoviesUseCases(
        moviesRepository: MovieRepository,

    ): GetAllMoviesUseCases {
        return GetAllMoviesUseCases(movieRepository = moviesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAllSlidesUseCases(
        moviesRepository: MovieRepository,

        ): GetAllSlidesUseCases {
        return GetAllSlidesUseCases(movieRepository = moviesRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetAllCastsUseCases(
        moviesRepository: MovieRepository,

        ): GetAllCastsUseCases {
        return GetAllCastsUseCases(movieRepository = moviesRepository)
    }



    @Provides
    @ViewModelScoped
    fun provideVideoMetaData() : VideoMetaData {
        return VideoMetaDataImpl()
    }

    @Provides
    @ViewModelScoped
    fun provideVideoPlayer(app : android.app.Application) : Player {
        return ExoPlayer.Builder(app).build()
    }





}