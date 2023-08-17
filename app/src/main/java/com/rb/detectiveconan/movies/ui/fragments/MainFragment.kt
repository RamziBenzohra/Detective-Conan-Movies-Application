package com.rb.detectiveconan.movies.ui.fragments


import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.google.android.material.tabs.TabLayoutMediator
import com.rb.detectiveconan.movies.R
import com.rb.detectiveconan.movies.databinding.FragmentMainBinding
import com.rb.detectiveconan.movies.presentation.MoviesViewModel
import com.rb.detectiveconan.movies.ui.adapters.MainMoviesRecyclerViewAdapter
import com.rb.detectiveconan.movies.ui.adapters.MovieSlidesViewPagerAdapter
import com.rb.detectiveconan.movies.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val moviesViewModel: MoviesViewModel by viewModels()

    @Inject
    lateinit var mainMoviesRecyclerViewAdapter: MainMoviesRecyclerViewAdapter

    @Inject
    lateinit var movieSlidesViewPagerAdapter: MovieSlidesViewPagerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.apply {
            myTb.inflateMenu(R.menu.menu_main)
            addCustomSearchView(myTb.menu)
            myTb.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.exit -> {
                        requireActivity().finish()
                        true
                    }
                    R.id.rate_us -> {
                        goToMarket()
                        true
                    }
                    R.id.search -> {

                        true
                    }
                    R.id.about -> {
                        showDialog()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }

            moviesRecycler.apply {
                adapter = mainMoviesRecyclerViewAdapter
                layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
                mainMoviesRecyclerViewAdapter.setOnItemClickListener { movie, imageView ->
                    val transitionExtras =
                        FragmentNavigatorExtras(imageView to "to_details_fragment")
                    val action =
                        MainFragmentDirections.actionMainFragmentToMoviesDetailsFragment(movie)
                    findNavController().navigate(action, navigatorExtras = transitionExtras)
                }
            }
            sliderPage.apply {
                adapter = movieSlidesViewPagerAdapter
                movieSlidesViewPagerAdapter.setOnItemClickListener { slide ->
                    val action =
                        MainFragmentDirections.actionMainFragmentToMoviePlayerFragment(slide)
                    findNavController().navigate(action)
                }
            }
            TabLayoutMediator(binding.tabIndicator,binding.sliderPage){tab, position ->
            }.attach()
        }
        moviesViewModel.allMovies.observe(viewLifecycleOwner) { movies ->
            mainMoviesRecyclerViewAdapter.submitList(movies)

        }
        moviesViewModel.allSlides.observe(viewLifecycleOwner) { slides ->
            movieSlidesViewPagerAdapter.submitList(slides)
            Log.d(
                "MAIN_FRAGMENT",
                "ADAPTER LIST OF SLIDES : ${movieSlidesViewPagerAdapter.currentList.size}"
            )
        }

        lifecycleScope.launch {
            binding.sliderPage.apply {
                while (true) {
                    delay(5000L)
                    when (currentItem) {
                        4 -> {
                            currentItem = 0
                        }
                        else -> {
                            currentItem++
                        }
                    }
                }
            }
        }
    }
    private fun addCustomSearchView(menu: Menu) {
        val search_Item = menu.findItem(R.id.search)
        if (search_Item != null) {
            var search_view = search_Item.actionView as SearchView
            var edit_text = search_view.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

            edit_text.hint = "Enter Number Or Name"
            edit_text.background =
                AppCompatResources.getDrawable(requireContext(), R.drawable.search_input_dark_style)
            edit_text.setTextColor(Color.WHITE)
            edit_text.setHintTextColor(Color.DKGRAY)
            edit_text.height = 25
            edit_text.textSize = 15f
            edit_text.setCompoundDrawablesWithIntrinsicBounds(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_search_black_24dp
                ), null, null, null
            )
            edit_text.compoundDrawablePadding = 8
            edit_text.setPadding(10, 3, 3, 3)
            search_view.onQueryTextChanged {
                moviesViewModel.searchText.value = it
                binding.sliderPage.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            }
        }
    }

    private fun goToMarket() {
        val appPackageName = requireContext().packageName
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (ex: android.content.ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }

    private fun showDialog() {
        val alertBuilder = AlertDialog.Builder(requireContext())
        val layoutInflation =
            LayoutInflater.from(requireContext()).inflate(R.layout.about_layout, null, false)
        alertBuilder.apply {
            setView(layoutInflation)
        }

        val alertCreate = alertBuilder.create()
        val btn = layoutInflation.findViewById<Button>(R.id.rateus_button)
        btn.setOnClickListener {

            alertCreate.dismiss()
            goToMarket()
        }
        alertCreate.show()
    }
}
