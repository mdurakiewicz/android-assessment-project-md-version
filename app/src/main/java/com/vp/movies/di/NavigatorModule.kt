package com.vp.movies.di

import com.vp.favorites.FavoriteNavigator
import com.vp.list.MovieListNavigator
import com.vp.movies.NavigatorProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigatorModule(private val navigatorProvider: NavigatorProvider) {

    @Singleton
    @Provides
    fun providesMovieListNavigator(): MovieListNavigator {
        return navigatorProvider.provideMovieListNavigator()
    }

    @Singleton
    @Provides
    fun provideFavoriteNavigator(): FavoriteNavigator {
        return navigatorProvider.provideFavoriteNavigator()
    }

}