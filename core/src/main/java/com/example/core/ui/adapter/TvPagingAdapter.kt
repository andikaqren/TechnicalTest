package com.example.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.business.domain.utils.AppConstant
import com.example.core.business.domain.utils.AppConstant.VIEW_TYPE_MINI
import com.example.core.business.domain.utils.setImagePlaceHolderGlideWithShimmer
import com.example.core.databinding.ItemProductBinding
import com.example.core.databinding.ItemProductThumbnailBinding
import com.example.core.ui.listener.ItemClickListener

class TvPagingAdapter(private val viewType: Int) :
    BasePagingAdapter<TV, BaseHolder<TV>>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TV>() {
            override fun areItemsTheSame(oldItem: TV, newItem: TV): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: TV, newItem: TV): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MiniViewHolder(listener: ItemClickListener<TV>, view: ItemProductBinding) :
        BaseHolder<TV>(listener, view.root) {
        val bind = view
        fun bind() = with(itemView) {
            itemData?.let {
                bind.shimmerLayout.startShimmer()
                val linkPoster =
                    AppConstant.POSTER_URL_500 + it.poster_path
                val rating: Float = it.vote_average!!.toFloat() * 0.5f
                bind.moviesMiniPoster.setImagePlaceHolderGlideWithShimmer(linkPoster,bind.shimmerLayout)
                bind.productMoviesTitle.text = it.name
                bind.productRated.rating = rating
            }
        }
    }

    inner class FullViewHolder(listener: ItemClickListener<TV>, view: ItemProductThumbnailBinding) :
        BaseHolder<TV>(listener, view.root) {
        val bind = view
        fun bind() = with(itemView) {
            itemData?.let {
                bind.shimmerLayout.startShimmer()
                val linkPoster =
                    AppConstant.POSTER_URL_500 + it.poster_path
                bind.movieThumbnail.setImagePlaceHolderGlideWithShimmer(linkPoster,bind.shimmerLayout)
            }
        }
    }

    override fun bindViewHolder(holder: BaseHolder<TV>?, data: TV?, position: Int) {
        when (this.viewType) {
            VIEW_TYPE_MINI -> {
                (holder as TvPagingAdapter.MiniViewHolder).bind()
            }
            else -> {
                (holder as TvPagingAdapter.FullViewHolder).bind()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<TV> {
        return when (this.viewType) {
            VIEW_TYPE_MINI -> {
                val itemBinding =
                    ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MiniViewHolder(
                    listener,
                    itemBinding
                )
            }
            else -> {
                val itemBinding =
                    ItemProductThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FullViewHolder(
                    listener,
                    itemBinding
                )
            }
        }
    }




}