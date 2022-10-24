package com.example.details.presentation.viewmore.crew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.details.databinding.FragmentViewMoreCrewsBinding
import com.example.details.presentation.viewmore.crew.adapter.ViewMoreCrewsAdapter
import kotlinx.coroutines.launch

class ViewMoreCrewsFragment : Fragment() {

    private var _binding: FragmentViewMoreCrewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ViewMoreCrewsAdapter
    private val viewModel: ViewMoreCrewsViewModel by viewModels()
    private val navArgs: ViewMoreCrewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewMoreCrewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.submitList(viewModel.generateCrewList(navArgs.crewList))
        }
    }

    private fun setupAdapter() {
        adapter = ViewMoreCrewsAdapter()
        binding.crewsRv.adapter = adapter

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}