package com.rb.detectiveconan.movies.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rb.detectiveconan.movies.data.entities.CastEntity
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import com.rb.detectiveconan.movies.data.entities.SlidesEntity
import com.rb.detectiveconan.movies.domain.GetAllCastsUseCases
import com.rb.detectiveconan.movies.domain.GetAllMoviesUseCases
import com.rb.detectiveconan.movies.domain.GetAllSlidesUseCases
import com.rb.detectiveconan.movies.domain.MoviesResult

import com.rb.detectiveconan.movies.ui.fragments.MoviesUiEvent

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel
@Inject
constructor(private val moviesUseCases: GetAllMoviesUseCases,
private val slidesUseCases: GetAllSlidesUseCases,
private val castsUseCases: GetAllCastsUseCases)
    :ViewModel() {


    val searchText = MutableStateFlow("")

    private val searchTitleFlow = searchText.flatMapLatest {
        moviesUseCases.getAllMovies(it)
    }

    val allMovies = searchTitleFlow.asLiveData()

    val allSlides = slidesUseCases.getAllSlides().asLiveData()

    private val _allCasts = MutableStateFlow<MoviesResult<List<CastEntity>>>(MoviesResult.Loading())
    val allCasts: StateFlow<MoviesResult<List<CastEntity>>> = _allCasts


    init {
    getAllMovies()
    getAllSlides()
}




    private fun getAllMovies()=viewModelScope.launch {
        Log.d("MOVIES_VIEW_MODEL","GET_ALL_MOVIES")
    moviesUseCases.catchMoviesOnDatabase()
    }

    private fun getAllSlides() = viewModelScope.launch {
        Log.d("MOVIES_VIEW_MODEL","GET_ALL_SLIDES")
        slidesUseCases.catchSlidesOnDatabase()
    }
    private fun getAllCasts(){
        Log.d("MOVIES_VIEW_MODEL","GET_ALL_CASTS")
        viewModelScope.launch {
            try {
//
            }catch (e:Exception){

            }
        }
    }


}