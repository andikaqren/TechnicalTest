package com.example.technicaltest.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.business.data.remote.model.RemoteVideo
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant.MOVIE
import com.example.core.business.domain.utils.gone
import com.example.core.business.domain.utils.visible
import com.example.core.ui.adapter.VideosAdapter
import com.example.core.ui.listener.ItemClickListener
import com.example.technicaltest.databinding.FragmentYoutubeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class YoutubeFragment : Fragment(), ItemClickListener<RemoteVideo> {

    private lateinit var binding: FragmentYoutubeBinding

    private var videoAdapter: VideosAdapter = VideosAdapter()
    private val viewModel: YoutubeViewModel by viewModels()
    private var id: Int? = null
    private var type: String = MOVIE

    companion object {
        private const val ARG_DATA = "ARG_DATA"
        private const val ARG_TYPE = "ARG_TYPE"
        fun newInstance(id: Int, type: String): YoutubeFragment {

            val args = Bundle().apply {
                putInt(ARG_DATA, id)
                putString(ARG_TYPE, type)
            }

            val fragment = YoutubeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYoutubeBinding.inflate(inflater)
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
        id?.let {  viewModel.reloadData(it, type) }

    }

    private fun observeView() {
        viewModel.videos.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Loading -> {
                    binding.progressBar.visible()
                }
                is DataState.Success -> {
                    binding.progressBar.gone()
                    val data = it.data.results.filter { it.site.equals("YouTube") }
                    if (data.size < 1) {
                        binding.tvTitle.text = "No Trailers Found"
                    }
                    videoAdapter.addAll(data.toMutableList())


                }
                is DataState.Error -> {
                    binding.progressBar.gone()
                    binding.tvTitle.text = "Something Error, Please Try Again Later"
                }
            }
        })
    }


    private fun initView() {
        binding.rvYoutubes.run {
            adapter = videoAdapter
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
        binding.progressBar.visible()
        videoAdapter.listener =this
    }

    override fun itemClick(position: Int, item: RemoteVideo?, view: Int) {

    }
}
