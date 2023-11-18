package com.rb.detectiveconan.movies.ui.adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.ViewGroup

import android.widget.ImageView

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.RequestManager
import com.rb.detectiveconan.movies.data.entities.MovieEntity

import com.rb.detectiveconan.movies.databinding.MoviePlayerItemBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class VideoPlayerRecyclerViewAdapter  @Inject constructor(
    private val glide: RequestManager,@ApplicationContext  val context: Context
    ):ListAdapter<MovieEntity,VideoPlayerRecyclerViewAdapter.VideoPlayerViewHolder>(MyDiffCallback()) {
    inner class VideoPlayerViewHolder(private val view: MoviePlayerItemBinding):RecyclerView.ViewHolder(view.root){
        fun bind (currentMovie:MovieEntity) {
            view.apply {
                itemTextMoviePlayer.text = currentMovie.title
                itemDescriptionMoviePlayer.text = currentMovie.description
                glide.load(currentMovie.thumbnail).into(itemImageMoviePlayer)
                root.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(currentMovie,itemImageMoviePlayer)
                    }
                }
            }
        }
    }

     class MyDiffCallback : DiffUtil.ItemCallback<MovieEntity>(){
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem.id==newItem.id

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) = oldItem==newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoPlayerViewHolder {
        val binding = MoviePlayerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VideoPlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoPlayerViewHolder, position: Int) {
        val currentMovie = getItem(position)
        holder.bind(currentMovie)
    }
    private var onItemClickListener: ((MovieEntity,ImageView) -> Unit)? = null
    fun setOnItemClickListener(listener: (MovieEntity,ImageView) -> Unit) {
        onItemClickListener = listener
    }
}