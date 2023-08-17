package com.rb.detectiveconan.movies.ui.adapters

import android.content.Context
import android.content.Entity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.compose.runtime.currentRecomposeScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import com.rb.detectiveconan.movies.databinding.ItemMovieBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class MainMoviesRecyclerViewAdapter  @Inject constructor(
    private val glide: RequestManager,@ApplicationContext  val context: Context
    ):ListAdapter<MovieEntity,MainMoviesRecyclerViewAdapter.MainMoviesViewHolder>(MyDiffCallback()) {
    inner class MainMoviesViewHolder(private val view: ItemMovieBinding):RecyclerView.ViewHolder(view.root){
        fun bind (currentMovie:MovieEntity) {
            view.apply {
                itemTV.text = currentMovie.title
                glide.load(currentMovie.thumbnail).into(itemIV)
                root.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(currentMovie,itemIV)
                    }
                }
            }
        }
    }

     class MyDiffCallback : DiffUtil.ItemCallback<MovieEntity>(){
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem.id==newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem==newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMoviesViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainMoviesViewHolder, position: Int) {
        val currentMovie = getItem(position)
        holder.bind(currentMovie)
    }
    private var onItemClickListener: ((MovieEntity,ImageView) -> Unit)? = null
    fun setOnItemClickListener(listener: (MovieEntity,ImageView) -> Unit) {
        onItemClickListener = listener
    }
}