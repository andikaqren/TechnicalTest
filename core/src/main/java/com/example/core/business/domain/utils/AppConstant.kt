package com.example.core.business.domain.utils

import com.example.core.BuildConfig


object AppConstant {
    const val baseUrl = BuildConfig.SERVER_URL
    const val movieDBKEY = BuildConfig.MOVIE_DB_KEY
    const val TABLE_GENRE = "Genre"
    const val TABLE_Movie = "Movie"
    const val MOVIE = "MOVIE"
    const val TYPE = "type"
    const val TV = "TV"
    const val ID = "id"
    const val EMPTY = ""
    const val POSTER_URL_500 = "https://image.tmdb.org/t/p/w500"
    const val UPCOMING_MOVIES = "Upcoming Movies"
    const val NOW_PLAYING_MOVIES = "Now Playing Movies"
    const val POPULAR_MOVIES = "Popular Movies"
    const val POPULAR_TV = "Popular TV"
    const val LATEST_TV = "Latest TV"
    const val PAGE_SIZE = 50
    const val UNKNOWN = "UNKNOWN"
    const val VIEW_TYPE_MINI = 1
    const val VIEW_TYPE_FULL = 2


}