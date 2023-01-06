package com.rb.detectiveconan.movies.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.rb.detectiveconan.movies.R
import com.rb.detectiveconan.movies.databinding.FragmentMainBinding
import com.rb.detectiveconan.movies.presentation.MoviesViewModel
import com.rb.detectiveconan.movies.ui.adapters.MainMoviesRecyclerViewAdapter
import com.rb.detectiveconan.movies.ui.adapters.MovieSlidesViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject




@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private lateinit var moviesViewModel: MoviesViewModel

    @Inject
    lateinit var mainMoviesRecyclerViewAdapter: MainMoviesRecyclerViewAdapter
    @Inject
    lateinit var movieSlidesViewPagerAdapter: MovieSlidesViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)
        moviesViewModel = ViewModelProvider(requireActivity())[MoviesViewModel::class.java]
        binding.sliderPage.apply {
            adapter = movieSlidesViewPagerAdapter
            movieSlidesViewPagerAdapter.setOnItemClickListener{
                val action = MainFragmentDirections.actionMainFragmentToMoviePlayerFragment(it)
                findNavController().navigate(action)
            }
        }
        binding.moviesRecycler.apply {
            adapter=mainMoviesRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL,false)
            mainMoviesRecyclerViewAdapter.setOnItemClickListener{
                val action = MainFragmentDirections.actionMainFragmentToMoviesDetailsFragment(it)
                findNavController().navigate(action)
            }
        }
        lifecycleScope.launch {
            moviesViewModel.allSlides.value.data.collect{
                movieSlidesViewPagerAdapter.slides =it
            }
            moviesViewModel.allMovies.value.data.collect{
                mainMoviesRecyclerViewAdapter.movies =it
            }
            Timer().scheduleAtFixedRate(object :TimerTask(){
                override fun run() {
                    if (binding.sliderPage.currentItem < binding.sliderPage.size - 1) {
                        binding.sliderPage.currentItem = binding.sliderPage.currentItem + 1
                    }else {
                        binding.sliderPage.currentItem = 0
                    }
                }

            },3000,5000)
            moviesViewModel.searchText.collect{searchText->
                if (searchText.isNotEmpty()){
                    binding.sliderPage.visibility=View.GONE
                    binding.textView.visibility=View.GONE
                    binding.indicator.visibility=View.GONE
                    mainMoviesRecyclerViewAdapter.filter.filter(searchText)
                }else{
                    binding.sliderPage.visibility=View.VISIBLE
                    binding.textView.visibility=View.VISIBLE
                    binding.indicator.visibility=View.VISIBLE
                }
            }

        }

    }
}