package com.example.tmdbapp.ui.topRated

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
import com.example.tmdbapp.databinding.FragmentTopRatedBinding

import com.example.tmdbapp.ui.home.HomeViewModel
import com.example.tmdbapp.ui.home.MovieAdapter
import com.example.tmdbapp.ui.home.MovieClickListener
import com.example.tmdbapp.ui.viewPager.ViewPagerFragmentDirections

class TopRatedFragment : Fragment() {
    private var _binding: FragmentTopRatedBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<TopRatedViewModel>()
    private lateinit var movieAdapter: TopRatedAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)

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
                movieAdapter = TopRatedAdapter(it, object : com.example.tmdbapp.ui.topRated.MovieClickListener {
                    override fun onMovieClicked(movieId: Int?) {
                        movieId?.let {
                            val action = ViewPagerFragmentDirections.actionViewPagerFragmentToDetailFragment(it)
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