package com.example.technicaltest.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.*
import com.example.core.business.domain.utils.AppConstant.EMPTY
import com.example.core.business.domain.utils.AppConstant.MOVIE
import com.example.core.business.domain.utils.AppConstant.POSTER_URL_500
import com.example.core.business.domain.utils.AppConstant.TV
import com.example.core.ui.adapter.MovieAdapter
import com.example.core.ui.adapter.TvAdapter
import com.example.core.ui.adapter.ViewPagerAdapter
import com.example.core.ui.listener.ItemClickListener
import com.example.technicaltest.R
import com.example.technicaltest.databinding.ActivityDetailBinding
import com.example.technicaltest.detail.fragment.DetailFragment
import com.example.technicaltest.detail.fragment.ReviewFragment
import com.example.technicaltest.detail.fragment.YoutubeFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailActivityViewModel by viewModels()
    private val pagerAdapter = ViewPagerAdapter(this)

    companion object {
        var type = MOVIE
        var id = EMPTY
        lateinit var movie: Movie
        lateinit var tv: TV
        var isFavourite = false
        fun start(context: Context, type: String, data: Any) {
            val intent = Intent(context, DetailActivity::class.java)
            when (type) {
                MOVIE -> {
                    movie = data as Movie
                    id = movie.id.toString()
                }
                TV -> {
                    tv = data as TV
                    id = tv.id.toString()
                }
            }
            this.type = type
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initPager()
        observeView()
    }


    private fun observeView() {
        when (type) {
            MOVIE -> {
                detailViewModel.favMovie.observe(this, {
                    when (it) {
                        is DataState.Loading -> binding.detailFrame.ivDetailFrameFav.gone()
                        is DataState.Error -> {
                            isFavourite = false
                            binding.detailFrame.ivDetailFrameFav.visible()
                            setFav()
                        }
                        is DataState.Success -> {
                            isFavourite = true
                            binding.detailFrame.ivDetailFrameFav.visible()
                            setFav()
                        }
                    }
                })

            }
            TV -> {
                detailViewModel.favTV.observe(this, {
                    when (it) {
                        is DataState.Loading -> binding.detailFrame.ivDetailFrameFav.gone()
                        is DataState.Error -> {
                            isFavourite = false
                            binding.detailFrame.ivDetailFrameFav.visible()
                            setFav()
                        }
                        is DataState.Success -> {
                            isFavourite = true
                            binding.detailFrame.ivDetailFrameFav.visible()
                            setFav()
                        }
                    }
                })


            }
        }

    }


    private fun initView() {
        binding.detailFrame.ivDetailFrameFav.setOnClickListener {
            isFavourite = !isFavourite
            when (type) {
                MOVIE -> detailViewModel.setFav(isFavourite, type, movie)
                TV -> detailViewModel.setFav(isFavourite, type, tv)
            }
            setFav()
            showSnackBar()
        }

        when (type) {
            MOVIE -> {
                initMovie()
            }
            TV -> {
                initTV()
            }
        }
    }

    private fun initMovie() {
        detailViewModel.reloadData(filename = movie.id)
        movie.vote_average?.toRating()?.let {
            binding.detailFrame.rbDetailFrame.rating = it
        }
        binding.detailFrame.tvDetailFrameTitle.text = movie.title
        movie.poster_path?.toBigPoster()
            ?.let { binding.detailFrame.ivDetailFramePoster.setImagePlaceHolderGlide(it) }
        movie.backdrop_path?.toBigPoster()
            ?.let { binding.detailFrame.ivDetailFrameCover.setImagePlaceHolderGlide(it) }

    }

    private fun initPager() {
        val detailFragment = when (type) {
            MOVIE-> DetailFragment.newInstanceMovie(movie)
            TV->DetailFragment.newInstanceTv(tv)
            else-> DetailFragment.newInstanceMovie(movie)
        }
        val youtubeFragment = when (type) {
            MOVIE-> YoutubeFragment.newInstance(movie.id,MOVIE)
            TV->YoutubeFragment.newInstance(tv.id, TV)
            else-> YoutubeFragment.newInstance(movie.id,MOVIE)
        }
        val reviewFragment = when (type) {
            MOVIE-> ReviewFragment.newInstance(movie.id,MOVIE)
            TV->ReviewFragment.newInstance(tv.id, TV)
            else-> ReviewFragment.newInstance(movie.id,MOVIE)
        }
        pagerAdapter.addFragment(detailFragment,"Overview")
        pagerAdapter.addFragment(youtubeFragment,"Trailers")
        pagerAdapter.addFragment(reviewFragment,"Reviews")
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = pagerAdapter.fragmentsTitle[position]
        }.attach()
    }

    private fun initTV() {
        detailViewModel.reloadDataTV(filename = tv.id)
        binding.detailFrame.tvDetailFrameTitle.text = tv.name
        tv.vote_average?.toRating()?.let {
            binding.detailFrame.rbDetailFrame.rating = it
        }

    }

    private fun setFav() {
        if (isFavourite) {
            binding.detailFrame.ivDetailFrameFav.setImageDrawable(
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.baseline_bookmark_24
                )
            )

        } else {
            binding.detailFrame.ivDetailFrameFav.setImageDrawable(
                ContextCompat.getDrawable(
                    this@DetailActivity,
                    R.drawable.baseline_bookmark_border_24
                )
            )
        }
    }

    private fun showSnackBar() {
        if (isFavourite) {
            Snackbar.make(
                binding.detailFrame.ivDetailFrameFav,
                "Success add to favorite",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            Snackbar.make(
                binding.detailFrame.ivDetailFrameFav,
                "Success remove from favorite",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }


}