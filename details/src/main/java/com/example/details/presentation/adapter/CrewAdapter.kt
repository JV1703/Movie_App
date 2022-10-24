package com.example.details.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.data.source.remote.response.movie_details.Crew
import com.example.core.utils.Constants
import com.example.details.databinding.CrewItemBinding
import com.example.movieapp.R

class CrewAdapter(private val clickListener: () -> Unit) : ListAdapter<Crew, CrewAdapter.CrewAdapterViewHolder>(DiffUtilCallback) {

    class CrewAdapterViewHolder(private val binding: CrewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(crew: Crew) {
            binding.apply {
                profilePic.load(Constants.BASE_IMG_URL + "w185" + crew.profilePath) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_error_placeholder)
                }
                name.text = crew.name
                department.text = crew.department
                job.text = crew.job
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CrewAdapterViewHolder(CrewItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CrewAdapterViewHolder, position: Int) {
        val crew = getItem(position)
        holder.bind(crew)
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Crew>() {
        override fun areItemsTheSame(oldItem: Crew, newItem: Crew): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Crew, newItem: Crew): Boolean {
            return oldItem == newItem
        }
    }
}