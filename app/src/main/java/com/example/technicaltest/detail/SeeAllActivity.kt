package com.example.technicaltest.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant.MOVIE
import com.example.core.business.domain.utils.AppConstant.POPULAR_MOVIES
import com.example.core.business.domain.utils.AppConstant.TV
import com.example.core.business.domain.utils.AppConstant.VIEW_TYPE_MINI
import com.example.core.business.domain.utils.gone
import com.example.core.ui.adapter.MoviePagingAdapter
import com.example.core.ui.adapter.TvPagingAdapter
import com.example.core.ui.listener.ItemClickListener
import com.example.technicaltest.databinding.ActivityAllBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SeeAllActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllBinding
    private var movieAdapter = MoviePagingAdapter(VIEW_TYPE_MINI)
    private var tvAdapter = TvPagingAdapter(VIEW_TYPE_MINI)
    private val viewModel: SeeAllActivityViewModel by viewModels()

    companion object {
        private var type = MOVIE
        private var subType = POPULAR_MOVIES
        fun start(context: Context, type: String, subType: String) {
            val intent = Intent(context, SeeAllActivity::class.java)
            this.type = type
            this.subType = subType
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initObserver()

    }

    private fun initView() {
        binding.tvTitle.text = subType
        binding.rvAllMovies.run {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
        binding.rvAllTv.run {
            adapter = tvAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
        movieAdapter.listener = object : ItemClickListener<Movie> {
            override fun itemClick(position: Int, item: Movie?, view: Int) {
                item?.let { DetailActivity.start(this@SeeAllActivity, MOVIE, item) }
            }
        }

        tvAdapter.listener = object : ItemClickListener<TV> {
            override fun itemClick(position: Int, item: TV?, view: Int) {
                item?.let { DetailActivity.start(this@SeeAllActivity, TV, item) }
            }
        }

        if (type == MOVIE) {
            binding.rvAllTv.gone()
            viewModel.reloadDataMovies(subType)
        } else {
            binding.rvAllMovies.gone()
            viewModel.reloadDataTV(subType)
        }
    }

    private fun initObserver() {
        viewModel.movie.observe(this, {
            when (it) {
                is DataState.Loading -> {
                    movieAdapter.submitData(lifecycle,viewModel.dummyPagingData)
                }
                is DataState.Error -> {
                    movieAdapter.submitData(lifecycle,viewModel.dummyErrorPagingData)
                }
                is DataState.Success -> {
                    movieAdapter.submitData(lifecycle, it.data)
                }

            }
        })

        viewModel.tv.observe(this, {
            when (it) {
                is DataState.Loading -> {
                    tvAdapter.submitData(lifecycle,viewModel.dummyLoadingPaging)
                }
                is DataState.Error -> {
                    tvAdapter.submitData(lifecycle,viewModel.dummyErrorPaging)
                }
                is DataState.Success -> {
                    tvAdapter.submitData(lifecycle, it.data)
                }

            }
        })
    }




}