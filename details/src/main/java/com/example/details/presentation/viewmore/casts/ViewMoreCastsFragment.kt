package com.example.details.presentation.viewmore.casts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.details.databinding.FragmentViewMoreCastsBinding
import com.example.details.presentation.viewmore.casts.adapter.ViewMoreCastsAdapter
import kotlinx.coroutines.launch

class ViewMoreCastsFragment : Fragment() {

    private var _binding: FragmentViewMoreCastsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ViewMoreCastsAdapter

    private val viewModel: ViewMoreCastsViewModel by viewModels()
    private val navArgs: ViewMoreCastsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewMoreCastsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        viewLifecycleOwner.lifecycleScope.launch {
            val data = viewModel.generateCastList(navArgs.castList)
            adapter.submitList(data)
        }
    }

    private fun setupAdapter() {
        adapter = ViewMoreCastsAdapter()
        binding.castsRv.adapter = adapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}