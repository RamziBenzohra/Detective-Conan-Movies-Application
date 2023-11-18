package com.rb.detectiveconan.movies.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.bumptech.glide.RequestManager
import com.rb.detectiveconan.movies.R
import com.rb.detectiveconan.movies.data.entities.SlidesEntity

import com.rb.detectiveconan.movies.databinding.FragmentMoviesDetailsBinding
import com.rb.detectiveconan.movies.presentation.ViewModel

import com.rb.detectiveconan.movies.ui.adapters.MovieCastsRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MoviesDetailsFragment : Fragment(R.layout.fragment_movies__details) {

    private val args by navArgs<MoviesDetailsFragmentArgs>()

    private lateinit var binding: FragmentMoviesDetailsBinding
    private lateinit var viewModel: ViewModel

    @Inject
    lateinit var movieCastsRecyclerViewAdapter: MovieCastsRecyclerViewAdapter

    @Inject
    lateinit var  glide: RequestManager



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesDetailsBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity())[ViewModel::class.java]

        val toDetailsAnimation = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.slide_left)
        sharedElementEnterTransition = toDetailsAnimation
        sharedElementReturnTransition = toDetailsAnimation

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
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        lifecycleScope.launch {
            viewModel.allCasts.collect{
                //movieCastsRecyclerViewAdapter.casts =it
            }
        }
    }



}