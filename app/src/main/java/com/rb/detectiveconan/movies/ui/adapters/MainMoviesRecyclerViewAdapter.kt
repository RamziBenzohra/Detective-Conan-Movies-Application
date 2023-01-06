package com.rb.detectiveconan.movies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rb.detectiveconan.movies.R
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import javax.inject.Inject

class MainMoviesRecyclerViewAdapter  @Inject constructor(
    private val glide: RequestManager
    ):RecyclerView.Adapter<MainMoviesRecyclerViewAdapter.MainMoviesViewHolder>(),Filterable {
    class MainMoviesViewHolder(view: View):RecyclerView.ViewHolder(view)

    private val diffCallback = object : DiffUtil.ItemCallback<MovieEntity>(){
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

    }



    private val listDiffer = AsyncListDiffer(this, diffCallback)

    var movies: List<MovieEntity>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    internal var moviesFiltered:List<MovieEntity> = movies

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMoviesViewHolder {
        return MainMoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainMoviesViewHolder, position: Int) {
        val movieEntity = moviesFiltered[position]
        holder.itemView.apply {

            findViewById<TextView>(R.id.itemIV).text=movieEntity.title
            glide.load(movieEntity.thumbnail).into(findViewById(R.id.itemIV))
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(movieEntity)
                }
            }

        }
    }
    private var onItemClickListener: ((MovieEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieEntity) -> Unit) {
        onItemClickListener = listener
    }
    override fun getItemCount(): Int {
        return moviesFiltered.size
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var charSearch=constraint.toString()
                moviesFiltered = if (charSearch.isEmpty()){
                    movies
                }else{
                    movies.filter { it.title.lowercase() == charSearch.lowercase() }
                }
                return FilterResults().apply {
                    values=moviesFiltered
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                results?.let {
                    moviesFiltered=it.values as List<MovieEntity>
                }
            }

        }
    }
}