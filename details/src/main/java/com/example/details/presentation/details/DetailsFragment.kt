package com.example.details.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.size.Scale
import com.example.core.data.source.remote.response.movie_details.Genre
import com.example.core.di.dependency.SharedDependency
import com.example.core.domain.model.MovieDetails
import com.example.core.utils.Constants.BANNER_PICTURE_SIZE
import com.example.core.utils.Constants.BASE_IMG_URL
import com.example.core.utils.Mapper
import com.example.details.databinding.FragmentDetailsBinding
import com.example.details.di.presentation.DaggerPresentationComponent
import com.example.details.presentation.adapter.CastAdapter
import com.example.details.presentation.adapter.CrewAdapter
import com.example.details.presentation.adapter.RecommendationAdapter
import com.example.details.presentation.adapter.VideoAdapter
import com.example.details.presentation.dialog.DialogsNavigator
import com.example.movieapp.R
import com.example.movieapp.utils.collectLatestLifecycleFlow
import com.example.movieapp.utils.makeToast
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: DetailsViewModelFactory
    private lateinit var dialogsNavigator: DialogsNavigator

    private val viewModel: DetailsViewModel by activityViewModels {
        factory
    }
    private lateinit var castAdapter: CastAdapter
    private lateinit var crewAdapter: CrewAdapter
    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var videosAdapter: VideoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        dialogsNavigator = DialogsNavigator(childFragmentManager)
        DaggerPresentationComponent.builder().context(requireContext()).appDependencies(
            EntryPointAccessors.fromApplication(
                requireActivity().applicationContext, SharedDependency::class.java
            )
        ).build().inject(this)
        activity?.intent?.getIntExtra("movieId", 0)?.let {
            viewModel.saveMovieId(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        collectLatestLifecycleFlow(viewModel.uiState) { uiState ->

            val details = uiState.details
            val bookmark = uiState.isBookmarked
            val isLoading = uiState.isLoading
            val initialLoad = uiState.initialLoad

            binding.lottie.isGone = if (initialLoad) {
                !isLoading
            } else {
                true
            }

            binding.scrollView.isGone = if (initialLoad) {
                isLoading
            } else {
                false
            }
            if (!isLoading) {
                binding.scrollView.scrollTo(0, 0)
            }

            binding.backdropIv.load(BASE_IMG_URL + BANNER_PICTURE_SIZE + details?.posterPath) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_error_placeholder)
                scale(Scale.FILL)
            }
            if (bookmark) {
                binding.bookmark.setImageResource(R.drawable.ic_bookmark_fill)
            } else {
                binding.bookmark.setImageResource(R.drawable.ic_bookmark_no_fill)
            }
            binding.bookmark.setOnClickListener {
                if (bookmark) {
                    viewModel.deleteSavedMovie(details)
                } else {
                    viewModel.saveMovie(details)
                }
            }
            binding.title.text = details?.title
            binding.details.text = getString(
                com.example.details.R.string.movie_details,
                getReleaseYear(details?.releaseDate),
                getGenres(details?.genres),
                getRuntime(details?.runtime)
            )
            binding.ratings.rating = (details?.voteAverage?.div(2))?.toFloat() ?: 0f
            binding.overview.text = details?.overview ?: "Unknown"
            if (details != null) {
                val data = uiState.details
                submitLists(data)
                viewMoreListener(data)
            }

            if (!uiState.userMessage.isNullOrEmpty()) {
                makeToast(uiState.userMessage)
                binding.lottie.setAnimation(com.example.core.R.raw.error)
                binding.lottie.visibility = View.VISIBLE
                binding.scrollView.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        tearDownAdapter()
        _binding = null
        super.onDestroyView()
    }

    private fun setupAdapter() {
        castAdapter = CastAdapter {}
        crewAdapter = CrewAdapter {}
        recommendationAdapter = RecommendationAdapter {
            viewModel.saveMovieId(it)
        }
        videosAdapter = VideoAdapter {
            showVideoDialog(it.key)
        }

        binding.castRv.adapter = castAdapter
        binding.crewRv.adapter = crewAdapter
        binding.recommendationRv.adapter = recommendationAdapter
        binding.videosRv.adapter = videosAdapter
    }

    private fun tearDownAdapter() {
        binding.castRv.adapter = null
        binding.crewRv.adapter = null
        binding.recommendationRv.adapter = null
        binding.videosRv.adapter = null
    }

    private fun submitLists(movieDetails: MovieDetails) {
        castAdapter.submitList(movieDetails.casts)
        crewAdapter.submitList(movieDetails.crews)
        recommendationAdapter.submitList(movieDetails.recommendations)
        videosAdapter.submitList(movieDetails.videos)
    }

    private fun viewMoreListener(movieDetails: MovieDetails) {
        binding.viewMoreCast.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToViewMoreCastsFragment(
                Mapper.toCastList(movieDetails)
            )
            findNavController().navigate(action)
        }
        binding.viewMoreCrew.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToViewMoreCrewsFragment(
                Mapper.toCrewList(movieDetails)
            )
            findNavController().navigate(action)
        }
    }

    private fun getReleaseYear(releaseData: String?): String {
        return releaseData?.substring(0, 4) ?: "Unknown"
    }

    private fun getGenres(genres: List<Genre>?): String {
        return genres?.joinToString { it.name } ?: "Unknown"
    }

    private fun getRuntime(runtime: Int?): String {
        return if (runtime != null) {
            val hours = runtime / 60
            val minutes = runtime % 60
            "${hours}h ${minutes}m"
        } else {
            "Unknown"
        }
    }

    private fun showVideoDialog(videoId: String) {
        dialogsNavigator.showVideoDialog(videoId)
    }
}