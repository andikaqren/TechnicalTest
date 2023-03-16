package com.example.core.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.business.domain.utils.AppConstant.POSTER_URL_500
import com.example.core.business.domain.utils.setImagePlaceHolderGlideWithShimmer
import com.example.core.databinding.ItemProductBigBinding
import com.example.core.ui.listener.ItemClickListener
import com.facebook.shimmer.ShimmerFrameLayout
import java.util.ArrayList

class MoviePagerAdapter :
    PagerAdapter() {
    var listener: ItemClickListener<Movie>? = null
    var list: List<Movie> = ArrayList()

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object`
    }

    fun setData(movies: List<Movie>) {
        this.list = movies
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val bind =
            ItemProductBigBinding.inflate(LayoutInflater.from(container.context), container, false)
        with(bind.root) {
            bind.shimmerLayout.startShimmer()
            val linkPoster =
                POSTER_URL_500 + list[position].backdrop_path
            list[position].backdrop_path?.let {
                if (it.isNotEmpty()) {
                    bind.itemHomePromoIv.setImagePlaceHolderGlideWithShimmer(
                        linkPoster,
                        bind.shimmerLayout
                    )
                }
            }
            bind.starText.visibility = View.VISIBLE
            list[position].vote_average?.let {
                val rating = it * 0.5
                bind.starText.text = rating.toString()
            }
            bind.itemHomePromoName.text = list[position].title

            container.addView(bind.root)
        }
        bind.root.setOnClickListener {
            listener?.itemClick(position, list[position], it.id)
        }

        return bind.root
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as ShimmerFrameLayout)
    }

}