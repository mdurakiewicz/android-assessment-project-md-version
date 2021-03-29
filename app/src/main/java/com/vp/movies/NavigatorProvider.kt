package com.vp.movies

import com.vp.list.DetailsNavigator

interface NavigatorProvider {
    fun provideDetailsNavigator(): DetailsNavigator
}