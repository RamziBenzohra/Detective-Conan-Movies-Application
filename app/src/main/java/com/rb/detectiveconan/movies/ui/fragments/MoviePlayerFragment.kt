package com.rb.detectiveconan.movies.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.ui.PlayerView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.rb.detectiveconan.movies.R
import com.rb.detectiveconan.movies.databinding.FragmentMainBinding
import com.rb.detectiveconan.movies.databinding.FragmentMoviesPlayerBinding
import com.rb.detectiveconan.movies.presentation.ViewModel
import com.rb.detectiveconan.movies.ui.adapters.VideoPlayerRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviePlayerFragment : Fragment(R.layout.fragment_movies__player) {


    private val args by navArgs<MoviePlayerFragmentArgs>()

    private lateinit var binding: FragmentMoviesPlayerBinding
    private val viewModel: ViewModel by viewModels()

    @Inject
    lateinit var videoPlayerRecyclerViewAdapter: VideoPlayerRecyclerViewAdapter
    private lateinit var mMoviesPlayer:PlayerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentMoviesPlayerBinding.bind(view)

        binding.apply {
            mMoviesPlayer = moviesPlayer
            mMoviesPlayer.player = viewModel.player
            playVideo(args.slideEntity.link)
            moviesPlayerRecyclerView.apply {
                adapter = videoPlayerRecyclerViewAdapter
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                videoPlayerRecyclerViewAdapter.setOnItemClickListener { movie, imageView ->
                    Log.d("MOVIES_VIEW_MODEL","playing video excuted ${movie.title} ........")
                    playVideo(movie.streaminglink)

                }
            }
        }

        viewModel.allMovies.observe(viewLifecycleOwner) { movies ->
            videoPlayerRecyclerViewAdapter.submitList(movies)

        }

    }
    private fun playVideo(path:String?){
        viewModel.apply {
            path?.let {
                addVideoUri(it)
            }
        }
    }

    override fun onStart() {
        viewModel.player.playWhenReady = true

        super.onStart()
    }

    override fun onStop() {
        viewModel.player.playWhenReady = false
        super.onStop()
    }

}