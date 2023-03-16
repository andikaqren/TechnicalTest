package com.example.technicaltest.detail.fragment

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant
import com.example.core.business.domain.utils.AppConstant.MOVIE
import com.example.core.business.domain.utils.visible
import com.example.core.ui.adapter.MovieAdapter
import com.example.core.ui.adapter.TvAdapter
import com.example.core.ui.listener.ItemClickListener
import com.example.technicaltest.databinding.FragmentDetailBinding
import com.example.technicaltest.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private var recomendationTVAdapter = TvAdapter()
    private var recomendationAdapter = MovieAdapter()
    private var similarTVAdapter = TvAdapter()
    private var similarAdapter = MovieAdapter()
    var movie: Movie? =null
    var tv: TV? = null
    private var type = MOVIE

    private val viewModel: DetailViewModel by viewModels()

    companion object {
        private const val ARG_DATA = "ARG_DATA"
        private const val ARG_TYPE = "ARG_TYPE"

        fun newInstanceMovie(data: Movie): DetailFragment {
            val args = Bundle().apply {
                putParcelable(ARG_DATA, data)
                putString(ARG_TYPE, MOVIE)
            }

            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstanceTv(data: TV): DetailFragment {
            val args = Bundle().apply {
                putParcelable(ARG_DATA, data)
                putString(ARG_TYPE, AppConstant.TV)
            }
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        if (type == MOVIE) {
            movie = when (SDK_INT) {
                33 -> requireArguments().getParcelable(ARG_DATA, Movie::class.java)
                else -> @Suppress("DEPRECATION") requireArguments().getParcelable(ARG_DATA)
            }
        }
        if (type == AppConstant.TV) {
            tv = when (SDK_INT) {
                33 -> requireArguments().getParcelable(ARG_DATA, TV::class.java)
                else -> @Suppress("DEPRECATION") requireArguments().getParcelable(ARG_DATA)
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        when (type) {
            MOVIE -> {
                initMovie()
            }
            AppConstant.TV -> {
                initTV()
            }
        }
    }

    private fun initMovie() {
        movie?.id?.let { viewModel.reloadData(filename = it) }
        binding.detailReleaseDate.text = movie?.release_date
        binding.detailSummaryText.text = movie?.overview
        binding.detailTitle.text = movie?.original_title
        binding.detailRecomendationRv.run {
            adapter = recomendationAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        }
        binding.detailSimiliarRv.run {
            adapter = similarAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        }
        similarAdapter.listener = object : ItemClickListener<Movie> {
            override fun itemClick(position: Int, item: Movie?, view: Int) {
                item?.let { DetailActivity.start(requireContext(), type, it) }
            }

        }
        recomendationAdapter.listener = object : ItemClickListener<Movie> {
            override fun itemClick(position: Int, item: Movie?, view: Int) {
                item?.let { DetailActivity.start(requireContext(), type, it) }
            }
        }
    }

    private fun initTV() {
        tv?.id?.let { viewModel.reloadDataTV(filename = it) }
        binding.detailRecomendationRv.run {
            adapter = recomendationTVAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.detailSimiliarRv.run {
            adapter = similarTVAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        similarTVAdapter.listener = object : ItemClickListener<TV> {
            override fun itemClick(position: Int, item: TV?, view: Int) {
                item?.let { DetailActivity.start(requireContext(), type, it) }
            }

        }
        recomendationTVAdapter.listener = object : ItemClickListener<TV> {
            override fun itemClick(position: Int, item: TV?, view: Int) {
                item?.let { DetailActivity.start(requireContext(), type, it) }
            }
        }
        binding.detailReleaseDate.text = tv?.first_air_date
        binding.detailSummaryText.text = tv?.overview
        binding.detailTitle.text = tv?.original_name
    }

    fun initObserver() {
        when (type) {
            MOVIE -> {
                viewModel.recomendationMovies.observe(viewLifecycleOwner, {
                    when (it) {
                        is DataState.Loading -> {

                        }
                        is DataState.Success -> {
                            recomendationAdapter.list = it.data.results.toMutableList()
                            recomendationAdapter.notifyDataSetChanged()
                            if (recomendationAdapter.list.isNullOrEmpty()) binding.detailRecomendationTextCommingSoon.visible()

                        }
                        is DataState.Error -> {


                        }
                    }
                })
                viewModel.similarMovies.observe(viewLifecycleOwner, {
                    when (it) {
                        is DataState.Loading -> {

                        }
                        is DataState.Success -> {

                            similarAdapter.list = it.data.results.toMutableList()
                            similarAdapter.notifyDataSetChanged()
                            if (similarAdapter.list.isNullOrEmpty()) binding.detailSimiliarTextCommingSoon.visible()

                        }
                        is DataState.Error -> {
                            binding.detailSimiliarTextCommingSoon.visible()
                        }
                    }
                })
            }
            AppConstant.TV -> {
                viewModel.recomendationTV.observe(viewLifecycleOwner, {
                    when (it) {
                        is DataState.Loading -> {

                        }
                        is DataState.Success -> {

                            recomendationTVAdapter.list = it.data.results.toMutableList()
                            recomendationTVAdapter.notifyDataSetChanged()
                            if (recomendationTVAdapter.list.isNullOrEmpty()) binding.detailRecomendationTextCommingSoon.visible()

                        }
                        is DataState.Error -> {
                            binding.detailRecomendationTextCommingSoon.visible()

                        }
                    }
                })
                viewModel.similarTV.observe(viewLifecycleOwner, {
                    when (it) {
                        is DataState.Loading -> {

                        }
                        is DataState.Success -> {

                            similarTVAdapter.list = it.data.results.toMutableList()
                            similarTVAdapter.notifyDataSetChanged()
                            if (similarTVAdapter.list.isNullOrEmpty()) binding.detailSimiliarTextCommingSoon.visible()

                        }
                        is DataState.Error -> {
                            binding.detailSimiliarTextCommingSoon.visible()

                        }
                    }
                })

            }
        }
    }


}