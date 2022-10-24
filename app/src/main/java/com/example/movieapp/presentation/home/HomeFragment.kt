package com.example.movieapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.presentation.home.adapter.NowPlayingPageTransformer
import com.example.movieapp.presentation.home.adapter.NowPlayingViewPagerAdapter
import com.example.movieapp.presentation.home.adapter.PopularMoviesAdapter
import com.example.movieapp.presentation.home.adapter.TrendingMoviesAdapter
import com.example.movieapp.utils.collectLatestLifecycleFlow
import com.example.movieapp.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var nowPlayingAdapter: NowPlayingViewPagerAdapter
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var trendingMoviesAdapter: TrendingMoviesAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNowPlayingViewPager()
        setupPopularMoviesListAdapter()
        setupTrendingMoviesListAdapter()

        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            viewModel.getMovies(page = 1, timeWindow = "day")
        }

        collectLatestLifecycleFlow(viewModel.homeUiState) { uiState ->
            binding.lottie.isGone = !uiState.isLoading
            binding.nestedScroll.isGone = uiState.isLoading

            nowPlayingAdapter.submitList(uiState.nowPlayingMovies)
            popularMoviesAdapter.submitList(uiState.popularMovies)
            trendingMoviesAdapter.submitList(uiState.trendingMovies)

            if (!uiState.nowPlayingMsg.isNullOrEmpty()) {
                makeToast("nowPlayingMsg: ${uiState.nowPlayingMsg}")
            }

            if (!uiState.popularMoviesMsg.isNullOrEmpty()) {
                makeToast("popularMoviesMsg: ${uiState.popularMoviesMsg}")
            }

            if (!uiState.trendingMoviesMsg.isNullOrEmpty()) {
                makeToast("trendingMoviesMsg: ${uiState.trendingMoviesMsg}")
            }
        }
    }


    private fun setupNowPlayingViewPager() {
        nowPlayingAdapter = NowPlayingViewPagerAdapter {

            // jetpack navigation. It works, but it navigates to the home fragment of details_nav_graph
            // details fragment is then shown in nav host container of home activity
//            val bundle = Bundle()
//            bundle.putInt("movieId", it.id)
//            val destinationId = R.id.details_graph
//            findNavController().navigate(destinationId, bundle)

            // use deepLink
            val deepLink = Class.forName("com.example.details.presentation.DetailsActivity")
            val intent = Intent(requireContext(), deepLink)
            intent.putExtra("movieId", it.id)
            startActivity(intent)

            // using Uri, remember to update the manifest of the destination
//            val deepUri = "moviesapp://details?movieId=${it.id}"
//            val uri = Uri.parse(deepUri)
//            val intent = Intent(Intent.ACTION_VIEW, uri)
//            startActivity(intent)
        }
        binding.nowPlayingVp.adapter = nowPlayingAdapter
        binding.nowPlayingVp.offscreenPageLimit = 3
        binding.nowPlayingVp.setPageTransformer(NowPlayingPageTransformer(binding.nowPlayingVp.offscreenPageLimit))
    }

    private fun setupPopularMoviesListAdapter() {
        popularMoviesAdapter = PopularMoviesAdapter {
            val deepLink = Class.forName("com.example.details.presentation.DetailsActivity")
            val intent = Intent(requireContext(), deepLink)
            intent.putExtra("movieId", it.id)
            startActivity(intent)
        }
        binding.popularMoviesRv.adapter = popularMoviesAdapter
    }

    private fun setupTrendingMoviesListAdapter() {
        trendingMoviesAdapter = TrendingMoviesAdapter {
            val deepLink = Class.forName("com.example.details.presentation.DetailsActivity")
            val intent = Intent(requireContext(), deepLink)
            intent.putExtra("movieId", it.id)
            startActivity(intent)
        }
        binding.trendingMoviesRv.adapter = trendingMoviesAdapter
    }

    override fun onPause() {
        binding.refresh.isRefreshing = false
        super.onPause()
    }

    override fun onDestroyView() {
        binding.nowPlayingVp.adapter = null
        binding.popularMoviesRv.adapter = null
        binding.trendingMoviesRv.adapter = null
        _binding = null
        super.onDestroyView()
    }

}