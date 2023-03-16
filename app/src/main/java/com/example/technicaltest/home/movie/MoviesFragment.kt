package com.example.technicaltest.home.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant.MOVIE
import com.example.core.business.domain.utils.AppConstant.NOW_PLAYING_MOVIES
import com.example.core.business.domain.utils.AppConstant.POPULAR_MOVIES
import com.example.core.business.domain.utils.AppConstant.UPCOMING_MOVIES
import com.example.core.business.domain.utils.AppConstant.VIEW_TYPE_FULL
import com.example.core.business.domain.utils.AppConstant.VIEW_TYPE_MINI
import com.example.core.business.domain.utils.gone
import com.example.core.business.domain.utils.visible
import com.example.core.ui.adapter.MoviePagerAdapter
import com.example.core.ui.adapter.MoviePagingAdapter
import com.example.core.ui.listener.ItemClickListener
import com.example.technicaltest.databinding.FragmentMoviesBinding
import com.example.technicaltest.detail.DetailActivity
import com.example.technicaltest.detail.SeeAllActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesFragment : Fragment(), ItemClickListener<Movie> {
    private lateinit var binding: FragmentMoviesBinding
    private var nowPlayingAdapter = MoviePagingAdapter(VIEW_TYPE_FULL)
    private var popularAdapter = MoviePagingAdapter(VIEW_TYPE_MINI)
    private var upcomingAdapter = MoviePagingAdapter(VIEW_TYPE_MINI)
    private var topRatedAdapter = MoviePagerAdapter()
    private var topRatedDummyAdapter = MoviePagerAdapter()
    private val viewModel: MoviesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
    }


    fun initView() {
        binding.container.rvUpcoming.run {
            adapter = upcomingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.container.rvNowPlaying.run {
            adapter = nowPlayingAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        binding.container.rvPopular.run {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        popularAdapter.listener = this
        upcomingAdapter.listener = this
        nowPlayingAdapter.listener = this
        topRatedAdapter.listener = this

        binding.homeVp.adapter = topRatedAdapter
        binding.dummyVp.adapter = topRatedDummyAdapter
        binding.homeVp.gone()


        binding.container.tvUpcomingAll.setOnClickListener {
            SeeAllActivity.start(requireContext(), MOVIE, UPCOMING_MOVIES)
        }
        binding.container.tvNowPlayingAll.setOnClickListener {
            SeeAllActivity.start(requireContext(), MOVIE, NOW_PLAYING_MOVIES)
        }
        binding.container.tvPopularAll.setOnClickListener {
            SeeAllActivity.start(requireContext(), MOVIE, POPULAR_MOVIES)
        }




    }

    fun initObserver() {
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    nowPlayingAdapter.submitData(lifecycle,viewModel.dummyPagingData)
                }
                is DataState.Error -> {
                    nowPlayingAdapter.submitData(lifecycle,viewModel.dummyErrorPagingData)
                }
                is DataState.Success -> {
                    nowPlayingAdapter.submitData(lifecycle, it.data)
                }
            }
        })

        viewModel.popularMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {

                    popularAdapter.submitData(lifecycle,viewModel.dummyPagingData)
                }
                is DataState.Error -> {
                    popularAdapter.submitData(lifecycle,viewModel.dummyErrorPagingData)
                }
                is DataState.Success -> {
                    popularAdapter.submitData(lifecycle, it.data)

                }
            }
        })

        viewModel.upcomingMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                upcomingAdapter.submitData(lifecycle,viewModel.dummyPagingData)
                }
                is DataState.Error -> {
                    upcomingAdapter.submitData(lifecycle,viewModel.dummyErrorPagingData)
                }
                is DataState.Success -> {
                    upcomingAdapter.submitData(lifecycle, it.data)

                }
            }
        })
        viewModel.topMovies.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.dummyVp.visible()
                    binding.homeVp.gone()
                    topRatedDummyAdapter.setData(viewModel.dummyLoadingList)
                }
                is DataState.Error -> {
                    binding.dummyVp.visible()
                    binding.homeVp.gone()
                    topRatedDummyAdapter.setData(viewModel.dummyErrorList)
                }
                is DataState.Success -> {
                    topRatedAdapter.setData(it.data.results)
                    binding.dummyVp.gone()
                    binding.homeVp.visible()

                }
            }
        })
    }


    override fun onResume() {
        super.onResume()
        viewModel.reloadData()
    }

    override fun itemClick(position: Int, item: Movie?, view: Int) {
        item?.let {
            if(!item.original_title.equals("Loading") || !item.original_title.equals("Error")) DetailActivity.start(requireContext(), MOVIE,it)
        }
    }

}