package com.vp.movies.di

import com.vp.list.DetailsNavigator
import com.vp.movies.MoviesApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigatorModule(private val moviesApplication: MoviesApplication) {

    @Singleton
    @Provides
    fun providesDetailsNavigator(): DetailsNavigator {
        return moviesApplication.provideDetailsNavigator()
    }

}