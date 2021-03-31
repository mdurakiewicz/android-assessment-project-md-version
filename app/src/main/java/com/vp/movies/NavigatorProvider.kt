package com.vp.movies

import com.vp.favorites.FavoriteNavigator
import com.vp.list.MovieListNavigator

interface NavigatorProvider {
    fun provideMovieListNavigator(): MovieListNavigator
    fun provideFavoriteNavigator(): FavoriteNavigator
}