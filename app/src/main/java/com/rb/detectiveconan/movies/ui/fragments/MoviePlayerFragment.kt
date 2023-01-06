package com.rb.detectiveconan.movies.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.rb.detectiveconan.movies.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviePlayerFragment : Fragment(R.layout.fragment_movies__player) {
    private val args by navArgs<MoviePlayerFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}