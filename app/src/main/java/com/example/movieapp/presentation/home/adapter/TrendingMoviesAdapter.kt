package com.example.movieapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.domain.model.Movies
import com.example.core.utils.Constants
import com.example.movieapp.R
import com.example.movieapp.databinding.PopularMoviesItemsBinding

class TrendingMoviesAdapter(
    private val clickListener: (Movies) -> Unit
) : androidx.recyclerview.widget.ListAdapter<Movies, TrendingMoviesAdapter.PopularMoviesViewHolder>(
    DiffUtilCallback
) {

    class PopularMoviesViewHolder(private val binding: PopularMoviesItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movies) {
            binding.popularMoviesIv.load(Constants.BASE_IMG_URL + "w342" + item.posterPath) {
                placeholder(R.drawable.loading_animation)
                crossfade(Constants.CROSS_FADE_DURATION)
                error(R.drawable.ic_error_placeholder)
            }

            binding.ratingsInd.progress = (item.voteAverage * 10).toInt()
            binding.ratingsTv.text = binding.ratingsTv.context.getString(
                R.string.ratings, (item.voteAverage * 10).toInt()
            )
            binding.titleTv.text = item.title
            binding.releaseDateTv.text = item.releaseDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PopularMoviesViewHolder(
            PopularMoviesItemsBinding.inflate(
                layoutInflater, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { clickListener(item) }
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem == newItem
        }
    }
}