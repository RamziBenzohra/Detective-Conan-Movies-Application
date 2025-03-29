package com.rb.detectiveconan.movies.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import com.rb.detectiveconan.movies.data.entities.CastEntity
import com.rb.detectiveconan.movies.data.player.VideoItem
import com.rb.detectiveconan.movies.data.player.VideoMetaData
import com.rb.detectiveconan.movies.domain.GetAllMoviesUseCases
import com.rb.detectiveconan.movies.domain.GetAllSlidesUseCases
import com.rb.detectiveconan.movies.domain.MoviesResult

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ViewModel
@Inject
constructor(private val moviesUseCases: GetAllMoviesUseCases,
private val slidesUseCases: GetAllSlidesUseCases,
            private val savedStateHandle: SavedStateHandle,
             val videoMetaData: VideoMetaData,
            val player : Player)
    :ViewModel(),Player.Listener {



    val videoUrisFlow = savedStateHandle.getStateFlow("videoUris", emptyList<Uri>())

    val videoItem = videoUrisFlow.map { uris->
        uris.map {uri->
            VideoItem(
                videoItem = uri,
                mediaItem = MediaItem.fromUri(uri)
            )
        }

    }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = emptyList())

    fun addVideoUri(path :String) = videoMetaData.apply {

        getVideoUri(path).apply {
            /////check if the URI does not exist
            ////// if the URI exist then we play directly the URI, we do not have to add new URI
            if (!videoUrisFlow.value.contains(this)){
                //add the URI is not exist
                Log.d("MOVIES_VIEW_MODEL","Adding the video to Media Item ........")

                savedStateHandle["videoUris"] = videoUrisFlow.value + this
                Log.d("MOVIES_VIEW_MODEL","There are ${videoUrisFlow.value.size} URI")
            }

            player.addMediaItem(MediaItem.fromUri(this))
            playVideo(this)
    }
    }
   private fun playVideo(uri: Uri){
        Log.d("MOVIES_VIEW_MODEL","Setting media Item ........")
       player.seekTo( videoUrisFlow.value.indexOf(uri), 0)
        player.prepare()
        player.playWhenReady = true
        player.play()
        Log.d("MOVIES_VIEW_MODEL","video is now playing ........")
    }


    override fun onPlaybackStateChanged(playbackState: Int) {
        when(playbackState){
            Player.STATE_BUFFERING ->{
                //show the progressBar

            }
            Player.STATE_READY ->{
                //hide the ProgressBar
            }
            Player.STATE_ENDED ->{
                //hide the ProgressBar
            }
        }

        super.onPlaybackStateChanged(playbackState)
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
        player.addListener(this)
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



}