package com.example.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.business.domain.utils.AppConstant.POSTER_URL_500
import com.example.core.business.domain.utils.AppConstant.VIEW_TYPE_FULL
import com.example.core.business.domain.utils.AppConstant.VIEW_TYPE_MINI
import com.example.core.business.domain.utils.setImagePlaceHolderGlideWithShimmer
import com.example.core.databinding.ItemProductBinding
import com.example.core.databinding.ItemProductThumbnailBinding
import com.example.core.ui.listener.ItemClickListener

class MoviePagingAdapter(val viewType: Int) :
    BasePagingAdapter<Movie, BaseHolder<Movie>>(
        DIFF_CALLBACK
    ) {




    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }



    inner class MiniViewHolder(listener: ItemClickListener<Movie>, view: ItemProductBinding) :
        BaseHolder<Movie>(listener, view.root) {
        val bind = view
        fun bind() = with(itemView) {
            itemData?.let {
                bind.shimmerLayout.startShimmer()
                val linkPoster =
                    POSTER_URL_500 + it.poster_path
                val rating: Float = it.vote_average!!.toFloat() * 0.5f
                bind.moviesMiniPoster.setImagePlaceHolderGlideWithShimmer(linkPoster,bind.shimmerLayout)
                bind.productMoviesTitle.text = it.title
                bind.productRated.rating = rating
            }
        }
    }

    inner class FullViewHolder(listener: ItemClickListener<Movie>, view: ItemProductThumbnailBinding) :
        BaseHolder<Movie>(listener, view.root) {
        val bind = view
        fun bind() = with(itemView) {
            bind.shimmerLayout.startShimmer()
            itemData?.let {
                val linkPoster =
                    POSTER_URL_500 + it.poster_path
                bind.movieThumbnail.setImagePlaceHolderGlideWithShimmer(linkPoster,bind.shimmerLayout)
            }
        }
    }


    override fun bindViewHolder(holder: BaseHolder<Movie>?, data: Movie?, position: Int) {
        when (this.viewType) {
            VIEW_TYPE_MINI -> {
                (holder as MiniViewHolder).bind()
            }
            else -> {
                (holder as FullViewHolder).bind()
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Movie> {
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