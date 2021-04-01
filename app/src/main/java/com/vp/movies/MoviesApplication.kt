package com.vp.movies

import android.app.Activity
import android.app.Application
import com.vp.detail.DetailActivity
import com.vp.favorites.FavoriteNavigator
import com.vp.list.MovieListNavigator
import com.vp.movies.di.AppModule
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
                .appModule(AppModule(this))
                .build()
                .inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = dispatchingActivityInjector

    override fun provideMovieListNavigator(): MovieListNavigator {
        return MovieListNavigator { activity, imdbID ->
            DetailActivity.start(activity, imdbID)
        }
    }

    override fun provideFavoriteNavigator(): FavoriteNavigator {
        return FavoriteNavigator { activity, imdbID ->
            DetailActivity.start(activity, imdbID)
        }
    }
}
