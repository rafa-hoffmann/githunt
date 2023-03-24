package com.sonder.githunt.repository_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sonder.githunt.feature.repository_list.R
import com.sonder.githunt.feature.repository_list.databinding.FragmentRepositoryListBinding
import com.sonder.githunt.repository_list.adapter.RepositoryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepositoryListFragment : Fragment() {

    private val viewModel: RepositoryListViewModel by viewModels()

    private var _binding: FragmentRepositoryListBinding? = null
    private val binding get() = _binding!!

    private var _adapter: RepositoryListAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        _adapter = RepositoryListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        binding.repositoryList.adapter = adapter
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.repositoriesState.collect {
                        when (it) {
                            is UiState.Success -> {
                                adapter.submitData(it.value)
                                updateProgressBar(loading = false)
                            }
                            is UiState.Error -> {
                                updateProgressBar(loading = false)
                                showToast(
                                    it.exception?.message
                                        ?: getString(R.string.repository_load_error)
                                )
                            }
                            UiState.Loading -> updateProgressBar(loading = true)
                            UiState.Initial -> updateProgressBar(loading = false)
                        }
                    }
                }
            }
        }
    }

    private fun updateProgressBar(loading: Boolean) {
        binding.repositoryProgressBar.isVisible = loading
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
