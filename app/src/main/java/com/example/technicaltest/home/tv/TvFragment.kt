package com.example.technicaltest.home.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant.LATEST_TV
import com.example.core.business.domain.utils.AppConstant.POPULAR_TV
import com.example.core.business.domain.utils.AppConstant.TV
import com.example.core.business.domain.utils.AppConstant.VIEW_TYPE_FULL
import com.example.core.business.domain.utils.AppConstant.VIEW_TYPE_MINI
import com.example.core.business.domain.utils.gone
import com.example.core.business.domain.utils.visible
import com.example.core.ui.adapter.TvPagerAdapter
import com.example.core.ui.adapter.TvPagingAdapter
import com.example.core.ui.listener.ItemClickListener
import com.example.technicaltest.databinding.FragmentMoviesBinding
import com.example.technicaltest.detail.DetailActivity
import com.example.technicaltest.detail.SeeAllActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TvFragment : Fragment(), ItemClickListener<TV> {
    private lateinit var binding: FragmentMoviesBinding
    private var latestAdapter = TvPagingAdapter(VIEW_TYPE_FULL)
    private var popularAdapter = TvPagingAdapter(VIEW_TYPE_MINI)
    private var topRatedAdapter = TvPagerAdapter()
    private var topRatedDummyAdapter = TvPagerAdapter()
    private val viewModel: TvViewModel by viewModels()
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


    private fun initView() {
        binding.container.tvUpcoming.gone()
        binding.container.rvUpcoming.gone()
        binding.container.tvUpcomingAll.gone()
        binding.container.tvNowPlaying.text ="Latest Series"
        binding.container.rvPopular.run {
            adapter = popularAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.container.rvNowPlaying.run {
            adapter = latestAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        topRatedAdapter.listener = this
        popularAdapter.listener = this
        latestAdapter.listener = this
        binding.homeVp.gone()
        binding.homeVp.adapter = topRatedAdapter
        binding.dummyVp.adapter = topRatedDummyAdapter
        binding.container.tvNowPlayingAll.setOnClickListener {
            SeeAllActivity.start(requireContext(),
                TV,
                LATEST_TV
            )
        }
        binding.container.tvPopularAll.setOnClickListener {
            SeeAllActivity.start(requireContext(), TV, POPULAR_TV)
        }
    }

    fun initObserver() {
        viewModel.latestTV.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    latestAdapter.submitData(lifecycle, viewModel.dummyLoadingPaging)
                }
                is DataState.Error -> {
                    latestAdapter.submitData(lifecycle, viewModel.dummyErrorPaging)
                }
                is DataState.Success -> {
                    latestAdapter.submitData(lifecycle, it.data)
                }
            }
        })
        viewModel.popularTV.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    latestAdapter.submitData(lifecycle, viewModel.dummyLoadingPaging)
                }
                is DataState.Error -> {
                    latestAdapter.submitData(lifecycle, viewModel.dummyErrorPaging)
                }
                is DataState.Success -> {
                    popularAdapter.submitData(lifecycle, it.data)

                }
            }
        })

        viewModel.topTV.observe(viewLifecycleOwner, {
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
                    binding.dummyVp.gone()
                    binding.homeVp.visible()
                    topRatedAdapter.setData( it.data.results)

                }
            }
        })

    }


    override fun onResume() {
        super.onResume()
        viewModel.reloadData()
    }



    override fun itemClick(position: Int, item: TV?, view: Int) {
        item?.let {
            if(!item.name.equals("Loading") || !item.name.equals("Error"))  DetailActivity.start(requireContext(), TV,it)
        }
    }

}