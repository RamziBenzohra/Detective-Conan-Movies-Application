package com.rb.detectiveconan.movies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rb.detectiveconan.movies.R
import com.rb.detectiveconan.movies.data.entities.CastEntity
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import javax.inject.Inject

class MovieCastsRecyclerViewAdapter  @Inject constructor(
    private val glide: RequestManager
    ):RecyclerView.Adapter<MovieCastsRecyclerViewAdapter.MovieCastsViewHolder>() {
    class MovieCastsViewHolder(view: View):RecyclerView.ViewHolder(view)

    private val diffCallback = object : DiffUtil.ItemCallback<CastEntity>(){
        override fun areItemsTheSame(oldItem: CastEntity, newItem: CastEntity): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: CastEntity, newItem: CastEntity): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun getChangePayload(oldItem: CastEntity, newItem: CastEntity): Any? {
            return super.getChangePayload(oldItem, newItem)
        }
    }



    private val listDiffer = AsyncListDiffer(this, diffCallback)

    var casts: List<CastEntity>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastsViewHolder {
        return MovieCastsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cast_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieCastsViewHolder, position: Int) {
        val castEntity = casts[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.actorTV).text=castEntity.name
            findViewById<TextView>(R.id.charTV).text=castEntity.carachter
            glide.load(castEntity.image).into(findViewById(R.id.actorIV))
        }
    }

    override fun getItemCount(): Int {
        return casts.size
    }
}