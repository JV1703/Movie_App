package com.example.bookmark.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bookmark.databinding.FragmentBookmarkBinding
import com.example.bookmark.di.presentation.DaggerPresentationComponent
import com.example.bookmark.presentation.adapter.BookmarkAdapter
import com.example.bookmark.presentation.adapter.SwipeToDeleteCallback
import com.example.core.di.dependency.SharedDependency
import com.example.core.domain.model.SavedMovie
import com.example.movieapp.utils.collectLatestLifecycleFlow
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class BookmarkFragment : Fragment() {

    @Inject
    lateinit var factory: BookmarkViewModelFactory

    private val viewModel: BookmarkViewModel by viewModels {
        factory
    }

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        DaggerPresentationComponent.builder().context(requireContext()).appDependencies(
            EntryPointAccessors.fromApplication(
                requireActivity().applicationContext, SharedDependency::class.java
            )
        ).build().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()

        collectLatestLifecycleFlow(viewModel.savedMovies) { savedMovies ->
            bookmarkAdapter.submitList(savedMovies)
            swipeToDelete(savedMovies, binding.bookmarkRv)
            if (savedMovies.isEmpty()) {
                binding.lottie.visibility = View.VISIBLE
                binding.bookmarkRv.visibility = View.GONE
            } else {
                binding.lottie.visibility = View.GONE
                binding.bookmarkRv.visibility = View.VISIBLE
            }
        }
    }

    private fun setupRv() {
        bookmarkAdapter = BookmarkAdapter {
            val deepLink = Class.forName("com.example.details.presentation.DetailsActivity")
            val intent = Intent(requireContext(), deepLink)
            intent.putExtra("movieId", it.id)
            startActivity(intent)
        }
        binding.bookmarkRv.adapter = bookmarkAdapter
    }

    private fun swipeToDelete(savedMovies: List<SavedMovie>, recyclerView: RecyclerView) {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = savedMovies[viewHolder.absoluteAdapterPosition]
                viewModel.deleteMovie(deletedItem.id)
                bookmarkAdapter.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                restoreDeletedData(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: SavedMovie) {
        val snackbar = Snackbar.make(
            view, "Deleted \"${deletedItem.title}\"", Snackbar.LENGTH_LONG
        )

        snackbar.setAction("Undo") {
            viewModel.saveMovie(deletedItem)
        }

        snackbar.show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}