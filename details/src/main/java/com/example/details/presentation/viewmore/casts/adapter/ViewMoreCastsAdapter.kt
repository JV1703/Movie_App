package com.example.details.presentation.viewmore.casts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.utils.Constants
import com.example.core.utils.Constants.BASE_IMG_URL
import com.example.core.utils.Constants.PROFILE_PICTURE_SIZE
import com.example.details.databinding.HeaderItemBinding
import com.example.details.databinding.ViewMoreCreditsItemBinding
import com.example.details.presentation.viewmore.casts.model.Items
import com.example.movieapp.R

class ViewMoreCastsAdapter : ListAdapter<Items, RecyclerView.ViewHolder>(DiffUtilCallback) {

    private val TYPE_HEADER = 0
    private val TYPE_CASTS = 1

    class CastsViewHolder(private val binding: ViewMoreCreditsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Items.CastDetails) {
            binding.profilePic.load(BASE_IMG_URL + PROFILE_PICTURE_SIZE + item.cast.profilePath) {
                placeholder(R.drawable.loading_animation)
                crossfade(Constants.CROSS_FADE_DURATION)
                error(R.drawable.ic_error_placeholder)
            }
            binding.name.text = item.cast.name
            binding.role.text = item.cast.character
        }
    }

    class HeaderViewHolder(private val binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Items.Header) {
            binding.apply {
                header.text = item.header
                headerDetails.text = item.headerDetails.toString()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Items.Header -> TYPE_HEADER
            is Items.CastDetails -> TYPE_CASTS
            else -> throw IllegalArgumentException("Invalid Item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                HeaderItemBinding.inflate(
                    layoutInflater, parent, false
                )
            )
            TYPE_CASTS -> CastsViewHolder(
                ViewMoreCreditsItemBinding.inflate(
                    layoutInflater, parent, false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HeaderViewHolder -> holder.bind(item as Items.Header)
            is CastsViewHolder -> holder.bind(item as Items.CastDetails)
        }
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Items, newItem: Items
        ): Boolean {
            return oldItem == newItem
        }
    }
}