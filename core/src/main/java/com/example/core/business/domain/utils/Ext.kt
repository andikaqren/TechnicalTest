package com.example.core.business.domain.utils

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.core.R
import com.example.core.business.data.local.model.LocalGenre
import com.example.core.business.data.local.model.LocalMovie
import com.example.core.business.data.local.model.LocalTv
import com.example.core.business.data.remote.model.RemoteGenre
import com.example.core.business.data.remote.model.RemoteMovie
import com.example.core.business.data.remote.model.RemoteTv
import com.example.core.business.domain.utils.AppConstant.POSTER_URL_500
import com.facebook.shimmer.ShimmerFrameLayout

fun LocalMovie.toMovie(): RemoteMovie = RemoteMovie(
    id = id,
    adult = adult,
    backdrop_path = backdrop_path,
    original_language = original_language,
    genre_ids = null,
    original_title = original_title,
    overview = overview,
    popularity = popularity,
    poster_path = poster_path,
    release_date = release_date,
    title = title,
    video = video,
    vote_average = vote_average,
    vote_count = vote_count
)

fun List<RemoteMovie>.toLocalMovieList(): List<LocalMovie> {
    val localMovies = mutableListOf<LocalMovie>()
    for (movie in this) {
        localMovies.add(
            LocalMovie(
                id = movie.id,
                adult = movie.adult,
                backdrop_path = movie.backdrop_path,
                original_language = movie.original_language,
                original_title = movie.original_title,
                overview = movie.overview,
                popularity = movie.popularity,
                poster_path = movie.poster_path,
                release_date = movie.release_date,
                title = movie.title,
                video = movie.video,
                vote_average = movie.vote_average,
                vote_count = movie.vote_count
            )
        )
    }
    return localMovies

}

fun List<RemoteTv>.toLocalTVList(): List<LocalTv> {
    val tvList = mutableListOf<LocalTv>()
    for (tv in this) {
        tvList.add(
            LocalTv(
                id = tv.id,
                backdrop_path = tv.backdrop_path,
                original_language = tv.original_language,
                original_name = tv.original_name,
                overview = tv.overview,
                popularity = tv.popularity,
                poster_path = tv.poster_path,
                first_air_date = tv.first_air_date,
                name = tv.name,
                video = tv.video,
                vote_average = tv.vote_average,
                vote_count = tv.vote_count
            )
        )
    }
    return tvList
}

fun List<LocalTv>.toTVList(): List<RemoteTv> {
    val tvList = mutableListOf<RemoteTv>()
    for (tv in this) {
        tvList.add(
            RemoteTv(
                id = tv.id,
                backdrop_path = tv.backdrop_path,
                original_language = tv.original_language,
                genre_ids = null,
                original_name = tv.original_name,
                overview = tv.overview,
                popularity = tv.popularity,
                poster_path = tv.poster_path,
                first_air_date = tv.first_air_date,
                name = tv.name,
                video = tv.video,
                vote_average = tv.vote_average,
                vote_count = tv.vote_count, origin_country = null
            )
        )
    }
    return tvList
}

fun RemoteMovie.toLocalMovie(): LocalMovie =
    LocalMovie(
        id = id,
        adult = adult,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )

fun RemoteTv.toLocalTV(): LocalTv =
    LocalTv(
        id = id,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_name = original_name,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        first_air_date = first_air_date,
        name = name,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count
    )

fun LocalTv.toTV(): RemoteTv =
    RemoteTv(
        id = id,
        backdrop_path = backdrop_path,
        original_language = original_language,
        original_name = original_name,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        first_air_date = first_air_date,
        name = name,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count, genre_ids = null, origin_country = null
    )

fun List<RemoteGenre>.toLocalGenres(): List<LocalGenre> {
    val localGenre = mutableListOf<LocalGenre>()
    for (genre in this) {
        localGenre.add(
            LocalGenre(
                id = genre.id,
                name = genre.name
            )
        )
    }
    return localGenre
}

fun List<LocalGenre>.toGenres(): List<RemoteGenre> {
    val localGenre = mutableListOf<RemoteGenre>()
    for (genre in this) {
        localGenre.add(RemoteGenre(id = genre.id, name = genre.name))
    }
    return localGenre
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun ImageView.setImagePlaceHolderGlide(img: String) {
    if (img.equals("Loading") || img.equals("Error")) {

    } else {
        Glide.with(getContext())
            .load(img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logomovie)
            .into(this)
    }
}

fun ImageView.setImagePlaceHolderGlideWithShimmer(
    img: String,
    shimmerFrameLayout: ShimmerFrameLayout
) {
    if (img.equals("Loading") || img.equals("Error")) {

    } else {
        Glide.with(getContext())
            .load(img)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logomovie)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    shimmerFrameLayout.stopShimmer()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    shimmerFrameLayout.stopShimmer()
                    shimmerFrameLayout.hideShimmer()
                    return false
                }

            })
            .into(this)
    }


}
fun String.toBigPoster():String{
    if(this.contains("www.gravatar.com")) return this
    return POSTER_URL_500 + this
}

fun Double.toRating():Float{
    return (this *0.5).toFloat()
}


