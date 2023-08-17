package com.rb.detectiveconan.movies.ui.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.rb.detectiveconan.movies.R
import com.rb.detectiveconan.movies.data.entities.MovieEntity
import com.rb.detectiveconan.movies.data.entities.SlidesEntity
import com.rb.detectiveconan.movies.databinding.ItemMovieBinding
import com.rb.detectiveconan.movies.databinding.SliderItemBinding
import javax.inject.Inject

class MovieSlidesViewPagerAdapter  @Inject constructor(
    private val glide: RequestManager
    ):ListAdapter<SlidesEntity, MovieSlidesViewPagerAdapter.MovieSlidesViewHolder>(MovieSlidesViewPagerAdapter.DiffCallback()) {
    inner class MovieSlidesViewHolder(private val view: SliderItemBinding):RecyclerView.ViewHolder(view.root){
        fun bind(currentSlide: SlidesEntity){
            view.apply {
                slideTV.text=currentSlide.title
                glide.load(currentSlide.image).into(slideIV)
                playFB.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(currentSlide)
                    }
                }

            }
        }

    }

    class DiffCallback() : DiffUtil.ItemCallback<SlidesEntity>(){
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





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSlidesViewPagerAdapter.MovieSlidesViewHolder {
        val binding = SliderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        binding.root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return MovieSlidesViewHolder(binding)
    }



    override fun onBindViewHolder(holder: MovieSlidesViewHolder, position: Int) {
        val currentSlide = getItem(position)
        holder.bind(currentSlide)


    }
    private var onItemClickListener: ((SlidesEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (SlidesEntity) -> Unit) {
        onItemClickListener = listener
    }

}