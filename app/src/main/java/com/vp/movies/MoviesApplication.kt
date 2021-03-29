package com.vp.movies

import android.app.Activity
import android.app.Application
import android.content.Intent
import com.vp.detail.DetailActivity
import com.vp.list.DetailsNavigator
import com.vp.movies.di.DaggerAppComponent
import com.vp.movies.di.NavigatorModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MoviesApplication : Application(), HasActivityInjector, NavigatorProvider {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .application(this)
                .navigatorModule(NavigatorModule(this))
                .build()
                .inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? = dispatchingActivityInjector

    override fun provideDetailsNavigator(): DetailsNavigator {
        return DetailsNavigator { activity, imdbID ->
            DetailActivity.start(activity, imdbID)
        }
    }
}
