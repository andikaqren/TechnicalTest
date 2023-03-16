package com.example.favourite

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
import com.example.core.business.domain.utils.AppConstant.MOVIE
import com.example.core.business.domain.utils.AppConstant.TV
import com.example.core.business.domain.utils.AppConstant.VIEW_TYPE_MINI
import com.example.core.business.domain.utils.gone
import com.example.core.business.domain.utils.visible
import com.example.core.business.interactors.ProductInteractors
import com.example.core.di.AppModule
import com.example.core.di.FavouriteModule
import com.example.core.ui.adapter.MoviePagingAdapter
import com.example.core.ui.adapter.TvPagingAdapter
import com.example.core.ui.listener.ItemClickListener
import com.example.favourite.databinding.FragmentFavoriteBinding
import com.example.favourite.di.DaggerFavouriteComponent
import com.example.visiprimanusantaratechnicaltest.detail.DetailActivity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FragmentFavourite : Fragment() {

    @Inject
    lateinit var interactors: ProductInteractors


    private var movieAdapter = MoviePagingAdapter(VIEW_TYPE_MINI)
    private var tvAdapter = TvPagingAdapter(VIEW_TYPE_MINI)
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DaggerFavouriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavouriteModule::class.java
                )
            )
            .build()
            .inject(this)
        favouriteViewModel.setApi(interactors)
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        favouriteViewModel.reloadData()

    }

    private fun initView() {
        tvAdapter.listener = object : ItemClickListener<TV> {
            override fun itemClick(position: Int, item: TV?, view: Int) {
                item?.let { DetailActivity.start(context!!, TV, item) }
            }

        }
        movieAdapter.listener = object : ItemClickListener<Movie> {
            override fun itemClick(position: Int, item: Movie?, view: Int) {
                item?.let { DetailActivity.start(context!!, MOVIE, item) }
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
                    binding.favouriteTvEmpty.gone()
                    binding.favouriteTvEmpty.gone()
                }
                is DataState.Success -> {
                    movieAdapter.submitData(lifecycle, it.data)
                }
                is DataState.Error -> {
                    //Temporary do nothing
                }
            }
        })

        favouriteViewModel.favouriteTV.observe(viewLifecycleOwner,
            {
                when (it) {
                    is DataState.Loading -> {
                        binding.favouriteTvEmpty.gone()
                        binding.titleFavouriteMovie.gone()
                    }
                    is DataState.Success -> {
                        tvAdapter.submitData(lifecycle, it.data)
                    }
                    is DataState.Error -> {
                        //Temporary do nothing
                    }
                }
            })
    }
}