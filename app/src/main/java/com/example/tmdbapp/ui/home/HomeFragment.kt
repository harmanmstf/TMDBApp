package com.example.tmdbapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tmdbapp.R
import com.example.tmdbapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        observeEvents()
        return binding.root
    }

    private fun observeEvents() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.textViewHomeError.text = it
            binding.progressBar.isVisible = true
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.movieList.observe(viewLifecycleOwner) { it ->
            if (it.isNullOrEmpty()) {
                binding.textViewHomeError.text = "There is no movie"
                binding.textViewHomeError.isVisible = true
            } else {
                movieAdapter = MovieAdapter(it, object : MovieClickListener {
                    override fun onMovieClicked(movieId: Int?) {
                        movieId?.let {
                            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                            findNavController().navigate(action)
                        }
                    }

                })
                binding.homeRecyclerView.adapter = movieAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}