package com.example.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.business.domain.utils.AppConstant
import com.example.core.business.domain.utils.setImagePlaceHolderGlideWithShimmer
import com.example.core.databinding.ItemProductBinding
import com.example.core.ui.listener.ItemClickListener

class MovieAdapter :
    BaseAdapter<Movie, MovieAdapter.ViewHolder>() {
    inner class ViewHolder(listener: ItemClickListener<Movie>, view: ItemProductBinding) :
        BaseHolder<Movie>(listener, view.root) {
        val bind = view
        fun bind() = with(itemView) {
            itemData?.let {
                bind.shimmerLayout.startShimmer()
                val linkPoster =
                    AppConstant.POSTER_URL_500 + it.poster_path
                val rating: Float = it.vote_average!!.toFloat() * 0.5f
                bind.moviesMiniPoster.setImagePlaceHolderGlideWithShimmer(linkPoster,bind.shimmerLayout)
                bind.productMoviesTitle.text = it.title
                bind.productRated.rating = rating
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            listener,
            itemBinding
        )
    }

    override fun bindViewHolder(holder: ViewHolder?, data: Movie?, position: Int) {
        holder?.bind()
    }

}