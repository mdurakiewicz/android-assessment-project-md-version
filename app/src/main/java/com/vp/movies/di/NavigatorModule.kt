package com.vp.movies.di

import com.vp.list.DetailsNavigator
import com.vp.movies.NavigatorProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigatorModule(private val navigatorProvider: NavigatorProvider) {

    @Singleton
    @Provides
    fun providesDetailsNavigator(): DetailsNavigator {
        return navigatorProvider.provideDetailsNavigator()
    }

}