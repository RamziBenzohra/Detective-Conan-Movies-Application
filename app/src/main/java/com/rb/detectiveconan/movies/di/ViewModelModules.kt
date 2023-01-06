package com.rb.detectiveconan.movies.di


import com.rb.detectiveconan.movies.data.local.MovieDAO
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








}