package com.example.tmdbapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import com.example.tmdbapp.MainActivity
import com.example.tmdbapp.R
import com.example.tmdbapp.databinding.FragmentDetailBinding
import com.example.tmdbapp.util.loadImage


class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        viewModel.getMovieDetail(movieId = args.movieId)
        observeEvents()
        return binding.root
    }

    private fun observeEvents() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBarDetail.isVisible = it
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.textViewErrorDetail.text = it
            binding.textViewErrorDetail.isVisible = true
        }
        viewModel.movieResponse.observe(viewLifecycleOwner) {movie ->
            with(binding){
                imageViewDetail.loadImage(movie.backdropPath)
                textViewDetailVote.text = "${movie.voteAverage.toString()} / ${movie.voteCount.toString()}"
                textViewDetailStudio.text = movie.productionCompanies?.first()?.name
                textViewDetailLanguage.text = movie.spokenLanguages?.first()?.englishName
                textViewDetailOverview.text = movie.overview
                textViewDetailGenre.text = movie.genres?.first()?.name
                textViewDetailReleasedate.text = "Release Date: ${movie.releaseDate}"
                textViewDetailRuntime.text = "Runtime: ${movie.runtime.toString()}"
                textViewDetailBudget.text = "Budget: ${movie.budget.toString()}$"
                textViewDetailRevenue.text = "Revenue: ${movie.revenue.toString()}$"
                movieDetailGroup.isVisible = true

                (requireActivity() as MainActivity).supportActionBar?.title = movie.title


            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
