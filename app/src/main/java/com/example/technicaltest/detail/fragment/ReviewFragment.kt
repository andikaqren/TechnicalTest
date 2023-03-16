package com.example.technicaltest.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.business.data.remote.model.RemoteReview
import com.example.core.business.data.remote.model.RemoteVideo
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant
import com.example.core.business.domain.utils.AppConstant.MOVIE
import com.example.core.business.domain.utils.gone
import com.example.core.business.domain.utils.visible
import com.example.core.ui.adapter.ReviewPagingAdapter
import com.example.core.ui.adapter.VideosAdapter
import com.example.core.ui.listener.ItemClickListener
import com.example.technicaltest.databinding.FragmentReviewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ReviewFragment : Fragment(), ItemClickListener<RemoteReview> {

    private lateinit var binding: FragmentReviewBinding

    private var reviewAdapter: ReviewPagingAdapter = ReviewPagingAdapter()
    private val viewModel: ReviewViewModel by viewModels()
    private var id: Int? = null
    private var type: String = MOVIE

    companion object {
        private const val ARG_DATA = "ARG_DATA"
        private const val ARG_TYPE = "ARG_TYPE"
        fun newInstance(id: Int, type: String): ReviewFragment {

            val args = Bundle().apply {
                putInt(ARG_DATA, id)
                putString(ARG_TYPE, type)
            }

            val fragment = ReviewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewBinding.inflate(inflater)
        id = requireArguments().getInt(ARG_DATA)
        type = requireArguments().getString(ARG_TYPE, MOVIE)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeView()
    }

    override fun onResume() {
        super.onResume()
        id?.let { viewModel.reloadData(it, type) }

    }

    private fun observeView() {

        reviewAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                LoadState.Loading -> {
                    //do nothing
                }
                is LoadState.Error -> {
                    binding.tvTitle.text = "Something Error"
                }
                is LoadState.NotLoading -> {
                    if (reviewAdapter.itemCount == 0) {
                        binding.tvTitle.text = "No Review Found"
                    } else {
                        binding.tvTitle.text = "Reviews"
                    }
                }
            }

        }

        viewModel.reviews.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.progressBar.visible()
                }
                is DataState.Success -> {
                    binding.progressBar.gone()
                    reviewAdapter.submitData(lifecycle, it.data)


                }
                is DataState.Error -> {
                    binding.progressBar.gone()
                    binding.tvTitle.text = "Something Error, Please Try Again Later"
                }
            }
        })
    }


    private fun initView() {
        binding.rvReviews.run {
            adapter = reviewAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        binding.progressBar.visible()
        reviewAdapter.listener = this
    }

    override fun itemClick(position: Int, item: RemoteReview?, view: Int) {

    }
}