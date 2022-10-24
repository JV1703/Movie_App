package com.example.movieapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.domain.model.Movies
import com.example.core.utils.Constants.BASE_IMG_URL
import com.example.core.utils.Constants.CROSS_FADE_DURATION
import com.example.movieapp.R
import com.example.movieapp.databinding.NowPlayingItemsBinding

class NowPlayingViewPagerAdapter(private val clickListener: (Movies) -> Unit) :
    ListAdapter<Movies, NowPlayingViewPagerAdapter.NowPlayingViewHolder>(DiffUtilCallback) {

    class NowPlayingViewHolder(private val binding: NowPlayingItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(item: Movies) {
            binding.nowPlayingIv.load(BASE_IMG_URL + "w342" + item.posterPath) {
                placeholder(R.drawable.loading_animation)
                crossfade(CROSS_FADE_DURATION)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NowPlayingViewHolder(NowPlayingItemsBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: NowPlayingViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding(item)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
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