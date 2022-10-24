package com.example.core.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.PagingLoadStateFooterViewHolderBinding

class PagingLoadStateFooterAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<PagingLoadStateFooterAdapter.PagingLoadStateViewHolder>() {

    inner class PagingLoadStateViewHolder(private val binding: PagingLoadStateFooterViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

    }

    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ): PagingLoadStateViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PagingLoadStateViewHolder(
            PagingLoadStateFooterViewHolderBinding.inflate(
                layoutInflater, parent, false
            )
        )
    }


}