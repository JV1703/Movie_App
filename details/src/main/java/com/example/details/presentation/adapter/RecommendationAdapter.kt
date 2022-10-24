package com.example.details.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.data.source.remote.response.movie_details.RecommendationResult
import com.example.core.utils.Constants
import com.example.core.utils.Constants.BASE_IMG_URL
import com.example.core.utils.Constants.RECOMMENDATION_PICTURE_SIZE
import com.example.movieapp.R
import com.example.movieapp.databinding.PopularMoviesItemsBinding

class RecommendationAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<RecommendationResult, RecommendationAdapter.RecommendationViewHolder>(
        DiffUtilCallback
    ) {

    class RecommendationViewHolder(
        private val binding: PopularMoviesItemsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(recommendation: RecommendationResult) {
            binding.apply {
                binding.popularMoviesIv.load(BASE_IMG_URL + RECOMMENDATION_PICTURE_SIZE + recommendation.posterPath) {
                    placeholder(R.drawable.loading_animation)
                    crossfade(Constants.CROSS_FADE_DURATION)
                    error(R.drawable.ic_error_placeholder)
                }

                binding.ratingsInd.progress = (recommendation.voteAverage?.times(10))?.toInt() ?: 0
                binding.ratingsTv.text = binding.ratingsTv.context.getString(
                    R.string.ratings, (recommendation.voteAverage?.times(10))?.toInt() ?: 0
                )
                binding.titleTv.text = recommendation.title
                binding.releaseDateTv.text = recommendation.releaseDate
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecommendationViewHolder(
            PopularMoviesItemsBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecommendationViewHolder, position: Int) {
        val recommendation = getItem(position)
        holder.bind(recommendation)
        holder.itemView.setOnClickListener {
            clickListener(recommendation.id)
        }
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<RecommendationResult>() {
        override fun areItemsTheSame(
            oldItem: RecommendationResult, newItem: RecommendationResult
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: RecommendationResult, newItem: RecommendationResult
        ): Boolean {
            return oldItem == newItem
        }
    }
}