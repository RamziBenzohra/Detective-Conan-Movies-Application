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
import com.rb.detectiveconan.movies.data.entities.SlidesEntity
import javax.inject.Inject

class MovieSlidesViewPagerAdapter  @Inject constructor(
    private val glide: RequestManager
    ):RecyclerView.Adapter<MovieSlidesViewPagerAdapter.MovieSlidesViewHolder>() {
    class MovieSlidesViewHolder(view: View):RecyclerView.ViewHolder(view)

    private val diffCallback = object : DiffUtil.ItemCallback<SlidesEntity>(){
        override fun areItemsTheSame(oldItem: SlidesEntity, newItem: SlidesEntity): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: SlidesEntity, newItem: SlidesEntity): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun getChangePayload(oldItem: SlidesEntity, newItem: SlidesEntity): Any? {
            return super.getChangePayload(oldItem, newItem)
        }
    }



    private val listDiffer = AsyncListDiffer(this, diffCallback)

    var slides: List<SlidesEntity>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSlidesViewHolder {
        return MovieSlidesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slider_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieSlidesViewHolder, position: Int) {
        val slideEntity = slides[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.slideTV).text=slideEntity.title
            glide.load(slideEntity.image).into(findViewById(R.id.slideIV))
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(slideEntity)
                }
            }
        }
    }
    private var onItemClickListener: ((SlidesEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (SlidesEntity) -> Unit) {
        onItemClickListener = listener
    }
    override fun getItemCount(): Int {
        return slides.size
    }
}