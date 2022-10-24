package com.example.details.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.data.source.remote.response.movie_details.VideosResult
import com.example.details.databinding.VideoItemBinding
import com.example.movieapp.R

class VideoAdapter(private val clickListener: (VideosResult) -> Unit) :
    ListAdapter<VideosResult, VideoAdapter.VideoViewHolder>(DiffUtilCallback) {

    class VideoViewHolder(private val binding: VideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(video: VideosResult) {
            binding.thumbnail.load("https://img.youtube.com/vi/${video.key}/mqdefault.jpg") {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VideoViewHolder(VideoItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = getItem(position)
        holder.bind(video)
        holder.itemView.setOnClickListener {
            clickListener(video)
        }
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<VideosResult>() {
        override fun areItemsTheSame(oldItem: VideosResult, newItem: VideosResult): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: VideosResult, newItem: VideosResult
        ): Boolean {
            return oldItem == newItem
        }
    }

}