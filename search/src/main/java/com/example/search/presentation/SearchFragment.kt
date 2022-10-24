package com.example.search.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.core.di.dependency.SharedDependency
import com.example.core.utils.PagingLoadStateFooterAdapter
import com.example.core.utils.PagingLoadStateHeaderAdapter
import com.example.movieapp.utils.collectLatestLifecycleFlow
import com.example.movieapp.utils.makeToast
import com.example.search.databinding.FragmentSearchBinding
import com.example.search.di.presentation.DaggerPresentationComponent
import com.example.search.presentation.adapter.SearchMoviesPagingAdapter
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory

    private val viewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var searchMoviesAdapter: SearchMoviesPagingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        DaggerPresentationComponent.builder().context(requireContext()).appDependencies(
            EntryPointAccessors.fromApplication(
                requireActivity().applicationContext, SharedDependency::class.java
            )
        ).build().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debouncingTextListener()
        setupAdapter()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            searchMoviesAdapter.loadStateFlow.collect { loadState ->
                // Show the retry state if initial load or refresh fails.
                binding.retryButton.isGone = loadState.source.refresh !is LoadState.Error
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            searchMoviesAdapter.loadStateFlow.collect { loadState ->

                binding.prependProgress.isVisible = loadState.source.prepend is LoadState.Loading
                binding.appendProgress.isVisible = loadState.source.append is LoadState.Loading

                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && searchMoviesAdapter.itemCount == 0
                // show empty list
                binding.lottie.isGone = !isListEmpty
                // Only show the list if refresh succeeds.
                binding.searchRv.isGone =
                    loadState.refresh is LoadState.Error || loadState.refresh is LoadState.Loading && searchMoviesAdapter.itemCount != 0
                // Show loading spinner during initial load or refresh.
                binding.paginationProgressBar.isVisible =
                    loadState.source.refresh is LoadState.Loading

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    makeToast("\uD83D\uDE28 Wooops ${it.error}")
                }
            }
        }

        binding.retryButton.setOnClickListener {
            searchMoviesAdapter.retry()
        }

        binding.searchFilter.setOnClickListener {
            makeToast("To be implemented")
        }
    }

    private fun debouncingTextListener() {
        var job: Job? = null
        binding.search.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(500L)
                editable?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.updateSearchQuery(it.toString().trim())
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        searchMoviesAdapter = SearchMoviesPagingAdapter {
            val deepLink = Class.forName("com.example.details.presentation.DetailsActivity")
            val intent = Intent(requireContext(), deepLink)
            intent.putExtra("movieId", it.id)
            startActivity(intent)
        }
        collectLatestLifecycleFlow(viewModel.searchQuery) {
            searchMoviesAdapter.submitData(it)
        }
        binding.searchRv.apply {
            adapter =
                searchMoviesAdapter.withLoadStateHeaderAndFooter(header = PagingLoadStateHeaderAdapter { searchMoviesAdapter.retry() },
                    footer = PagingLoadStateFooterAdapter { searchMoviesAdapter.retry() })
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}