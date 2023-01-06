package com.rb.detectiveconan.movies.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rb.detectiveconan.movies.R
import com.rb.detectiveconan.movies.data.entities.SlidesEntity

import com.rb.detectiveconan.movies.databinding.FragmentMoviesDetailsBinding
import com.rb.detectiveconan.movies.presentation.MoviesViewModel

import com.rb.detectiveconan.movies.ui.adapters.MovieCastsRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MoviesDetailsFragment : Fragment(R.layout.fragment_movies__details) {

    private val args by navArgs<MoviesDetailsFragmentArgs>()

    private lateinit var binding: FragmentMoviesDetailsBinding
    private lateinit var moviesViewModel: MoviesViewModel

    @Inject
    lateinit var movieCastsRecyclerViewAdapter: MovieCastsRecyclerViewAdapter

    @Inject
    lateinit var  glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMoviesDetailsBinding.inflate(layoutInflater)
        moviesViewModel = ViewModelProvider(requireActivity())[MoviesViewModel::class.java]
        binding.movieTitleTV.text = args.movieEntity.title
        binding.movieDescriptionTV.text = args.movieEntity.description
        binding.movieRatingTV.text = args.movieEntity.rating
        glide.load(args.movieEntity.thumbnail).into(binding.thumbnailIV)
        glide.load(args.movieEntity.thumbnail).centerCrop().into(binding.coverIV)
        binding.CastRV.apply {
            adapter=movieCastsRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false)
        }
        binding.playFab.setOnClickListener {
            val action = MoviesDetailsFragmentDirections.actionMoviesDetailsFragmentToMoviePlayerFragment(
                SlidesEntity(args.movieEntity.thumbnail, title = args.movieEntity.title,
                link = args.movieEntity.streaminglink,
                id = args.movieEntity.id)
            )
            findNavController().navigate(action)
        }
        lifecycleScope.launch {
            moviesViewModel.allCasts.value.data.collect{
                movieCastsRecyclerViewAdapter.casts =it
            }
        }
    }



}