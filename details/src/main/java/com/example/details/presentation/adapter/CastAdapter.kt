package com.example.details.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.data.source.remote.response.movie_details.Cast
import com.example.core.utils.Constants.BASE_IMG_URL
import com.example.details.databinding.CastItemBinding
import com.example.movieapp.R

class CastAdapter(private val clickListener: () -> Unit) : ListAdapter<Cast, CastAdapter.CreditsViewHolder>(DiffUtilCallback) {

    class CreditsViewHolder(private val binding: CastItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: Cast) {
            binding.profilePic.load(BASE_IMG_URL + "w185" + cast.profilePath) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_error_placeholder)
            }
            binding.name.text = cast.name
            binding.role.text = cast.character
            binding.department.text = cast.knownForDepartment
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CreditsViewHolder(CastItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CreditsViewHolder, position: Int) {
        val cast = getItem(position)
        holder.bind(cast)
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Cast, newItem: Cast
        ): Boolean {
            return oldItem == newItem
        }
    }
}