package com.example.movieapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.utils.Constants
import com.example.movieapp.R
import com.example.movieapp.databinding.NowPlayingItemsBinding
import com.example.core.data.source.remote.response.now_playing.Result

class NowPlayingListAdapter(private val clickListener: (Result) -> Unit) :
    ListAdapter<Result, NowPlayingListAdapter.NowPlayingViewHolder>(DiffUtilCallback) {

    class NowPlayingViewHolder(private val binding: NowPlayingItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(item: Result) {
            binding.nowPlayingIv.load(Constants.BASE_IMG_URL + "w342" + item.posterPath) {
                placeholder(R.drawable.loading_animation)
                crossfade(Constants.CROSS_FADE_DURATION)
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

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
}