package com.rb.detectiveconan.movies.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.rb.detectiveconan.movies.data.entities.CastEntity
import com.rb.detectiveconan.movies.data.player.VideoItem
import com.rb.detectiveconan.movies.data.player.VideoMetaData
import com.rb.detectiveconan.movies.domain.GetAllCastsUseCases
import com.rb.detectiveconan.movies.domain.GetAllMoviesUseCases
import com.rb.detectiveconan.movies.domain.GetAllSlidesUseCases
import com.rb.detectiveconan.movies.domain.MoviesResult

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModel
@Inject
constructor(private val moviesUseCases: GetAllMoviesUseCases,
private val slidesUseCases: GetAllSlidesUseCases,
            private val savedStateHandle: SavedStateHandle,
            private val videoMetaData: VideoMetaData,
            val player : Player)
    :ViewModel() {

    val videoUris = savedStateHandle.getStateFlow("videoUris", emptyList<Uri>())
    val videoItems = videoUris.map {uris->
        uris.map {uri->
            VideoItem(
                videoItem = uri,
                mediaItem = MediaItem.fromUri(uri)
            )
        }

    }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = emptyList())

    fun addVideoUri(path :String) = videoMetaData.getVideoUri(path).apply {

        savedStateHandle["videoUris"] = videoUris.value + this
        player.addMediaItem(MediaItem.fromUri(this))
    }
    fun playVideo(uri: Uri){
        player.setMediaItem(
            videoItems.value.find { it.videoItem == uri }?.mediaItem ?:return
        )
        player.playWhenReady = true
        player.play()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

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
        player.prepare()
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