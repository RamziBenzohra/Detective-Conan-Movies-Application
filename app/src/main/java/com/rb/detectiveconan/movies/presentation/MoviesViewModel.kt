package com.rb.detectiveconan.movies.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel
@Inject
constructor(private val moviesUseCases: GetAllMoviesUseCases,
private val slidesUseCases: GetAllSlidesUseCases,
private val castsUseCases: GetAllCastsUseCases)
    :ViewModel() {

    private val _search = MutableSharedFlow<String>()
    val searchText = _search.asSharedFlow()


    private val _allMovies = mutableStateOf(MoviesResult<List<MovieEntity>>(isLoading = false))
    val allMovies: State<MoviesResult<List<MovieEntity>>>
    get() {
        getAllMovies()
       return _allMovies
    }

    private val _allSlides = mutableStateOf(MoviesResult<List<SlidesEntity>>(isLoading = false))
    val allSlides: State<MoviesResult<List<SlidesEntity>>>
    get() {
        getAllSlides()
        return _allSlides
    }
    private val _allCasts = mutableStateOf(MoviesResult<List<CastEntity>>(isLoading = false))
    val allCasts: State<MoviesResult<List<CastEntity>>>
    get() {
        getAllcasts()
        return _allCasts
    }

     fun onUiTriggered(uiEvent: MoviesUiEvent<*>){
        when(uiEvent){
           is MoviesUiEvent.OnToast<*> ->{
               viewModelScope.launch {
                   _search.emit(uiEvent.data as String)
               }

           }
        }
    }


    private fun getAllMovies(){
        Log.d("MOVIES_VIEW_MODEL","GET_ALL_MOVIES")
        viewModelScope.launch {
            try {
                _allMovies.value = _allMovies.value.copy(isLoading = true)
                val movies = moviesUseCases.invoke()
                _allMovies.value = _allMovies.value.copy(
                    data = movies
                )

            }catch (e:Exception){
                _search.emit("Exception : ${e.localizedMessage}")
            }
        }
    }
    private fun getAllSlides(){
        Log.d("MOVIES_VIEW_MODEL","GET_ALL_SLIDES")
        viewModelScope.launch {
            try {
                _allSlides.value = _allSlides.value.copy(isLoading = true)
                val slides = slidesUseCases.invoke()
                _allSlides.value = _allSlides.value.copy(
                    data = slides
                )
            }catch (e:Exception){

            }
        }
    }
    private fun getAllcasts(){
        Log.d("MOVIES_VIEW_MODEL","GET_ALL_CASTS")
        viewModelScope.launch {
            try {
                _allCasts.value = _allCasts.value.copy(isLoading = true)
                val casts = castsUseCases.invoke()
                _allCasts.value = _allCasts.value.copy(
                    data = casts
                )
            }catch (e:Exception){

            }
        }
    }


}