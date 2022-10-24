package com.example.search.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.domain.model.SearchMovies
import com.example.core.utils.Constants
import com.example.movieapp.R
import com.example.search.databinding.SearchMoviesItemBinding

class SearchMoviesPagingAdapter(private val clickListener: (SearchMovies) -> Unit) :
    PagingDataAdapter<SearchMovies, SearchMoviesPagingAdapter.SearchMoviesViewHolder>(
        DiffUtilCallback
    ) {

    class SearchMoviesViewHolder(private val binding: SearchMoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchMovies) {
            binding.moviePoster.load(Constants.BASE_IMG_URL + "w342" + item.posterPath) {
                placeholder(R.drawable.loading_animation)
                crossfade(Constants.CROSS_FADE_DURATION)
                error(R.drawable.ic_error_placeholder)
            }

            binding.movieTitle.text = item.title
            binding.overview.text = item.overview
            binding.releaseDate.text = item.releaseDate
        }
    }

    override fun onBindViewHolder(holder: SearchMoviesViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                clickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchMoviesViewHolder(
            SearchMoviesItemBinding.inflate(
                layoutInflater, parent, false
            )
        )
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<SearchMovies>() {
        override fun areItemsTheSame(
            oldItem: SearchMovies, newItem: SearchMovies
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SearchMovies, newItem: SearchMovies
        ): Boolean {
            return oldItem == newItem
        }

    }
}