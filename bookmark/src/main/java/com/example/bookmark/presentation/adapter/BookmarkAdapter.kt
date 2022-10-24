package com.example.bookmark.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bookmark.databinding.BookmarkedMoviesItemBinding
import com.example.core.domain.model.SavedMovie
import com.example.core.utils.Constants
import com.example.movieapp.R

class BookmarkAdapter(private val clickListener: (SavedMovie) -> Unit) :
    ListAdapter<SavedMovie, BookmarkAdapter.BookmarkViewHolder>(DiffUtilCallback) {

    class BookmarkViewHolder(private val binding: BookmarkedMoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SavedMovie) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BookmarkViewHolder(
            BookmarkedMoviesItemBinding.inflate(
                layoutInflater, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }


    companion object DiffUtilCallback : DiffUtil.ItemCallback<SavedMovie>() {
        override fun areItemsTheSame(oldItem: SavedMovie, newItem: SavedMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SavedMovie, newItem: SavedMovie): Boolean {
            return oldItem == newItem
        }
    }
}