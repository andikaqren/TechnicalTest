package com.example.technicaltest.home.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant
import com.example.core.business.domain.utils.gone
import com.example.core.business.domain.utils.visible
import com.example.core.ui.adapter.MoviePagingAdapter
import com.example.core.ui.adapter.TvPagingAdapter
import com.example.core.ui.listener.ItemClickListener
import com.example.technicaltest.databinding.FragmentFavouriteBinding
import com.example.technicaltest.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FragmentFavourite : Fragment() {


    private var movieAdapter = MoviePagingAdapter(AppConstant.VIEW_TYPE_MINI)
    private var tvAdapter = TvPagingAdapter(AppConstant.VIEW_TYPE_MINI)
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private lateinit var binding: FragmentFavouriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        favouriteViewModel.reloadData()

    }

    private fun initView() {
        tvAdapter.listener = object : ItemClickListener<TV> {
            override fun itemClick(position: Int, item: TV?, view: Int) {
                item?.let { DetailActivity.start(context!!, AppConstant.TV, item) }
            }

        }
        movieAdapter.listener = object : ItemClickListener<Movie> {
            override fun itemClick(position: Int, item: Movie?, view: Int) {
                item?.let { DetailActivity.start(context!!, AppConstant.MOVIE, item) }
            }

        }
        binding.rvFavouriteMovie.run {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.rvFavouriteTv.run {
            adapter = tvAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }


    private fun initObserver() {
        movieAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (itemCount > 0) {
                    binding.favouriteMovieEmpty.gone()
                } else {
                    binding.favouriteMovieEmpty.visible()
                }
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                if (itemCount > 0) {
                    binding.favouriteMovieEmpty.gone()
                } else {
                    binding.favouriteMovieEmpty.visible()
                }
            }

        })
        tvAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (itemCount > 0) {
                    binding.favouriteTvEmpty.gone()
                } else {
                    binding.favouriteTvEmpty.visible()
                }
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                if (itemCount > 0) {
                    binding.favouriteTvEmpty.gone()
                } else {
                    binding.favouriteTvEmpty.visible()
                }
            }
        })
        favouriteViewModel.favouriteMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {

                }
                is DataState.Success -> {

                    movieAdapter.submitData(lifecycle, it.data)
                }
                is DataState.Error -> {
                    binding.favouriteMovieEmpty.visible()
                }
            }
        })

        favouriteViewModel.favouriteTV.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Loading -> {
                    }
                    is DataState.Success -> {
                        tvAdapter.submitData(lifecycle, it.data)
                    }
                    is DataState.Error -> {
                        binding.favouriteTvEmpty.visible()
                    }
                }
            })
    }


}