package com.example.core.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.core.business.data.remote.model.RemoteReview
import com.example.core.business.domain.utils.*
import com.example.core.databinding.ItemReviewBinding
import com.example.core.ui.listener.ItemClickListener

class ReviewPagingAdapter() :
    BasePagingAdapter<RemoteReview, BaseHolder<RemoteReview>>(
        DIFF_CALLBACK
    ) {


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RemoteReview>() {
            override fun areItemsTheSame(oldItem: RemoteReview, newItem: RemoteReview): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: RemoteReview, newItem: RemoteReview): Boolean {
                return oldItem == newItem
            }

        }
    }


    inner class ItemViewHolder(listener: ItemClickListener<RemoteReview>, view: ItemReviewBinding) :
        BaseHolder<RemoteReview>(listener, view.root) {
        val bind = view
        fun bind() = with(itemView) {
            itemData?.let {
                it.authorDetails?.avatarPath?.toBigPoster()?.let {  bind.userAvatar.setImagePlaceHolderGlide(it)}
                Log.d("DIKAA",it.authorDetails?.avatarPath?.toBigPoster().toString())
                it.content?.let { bind.reviewText.text= it }
                it.author?.let { bind.userName.text=it }
                it.authorDetails?.rating?.toRating()?.let { bind.reviewRating.rating = it }
            }
        }
    }


    override fun bindViewHolder(
        holder: BaseHolder<RemoteReview>?,
        data: RemoteReview?,
        position: Int
    ) {
        (holder as ItemViewHolder).bind()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<RemoteReview> {

        val itemBinding =
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(listener, itemBinding)
    }
}