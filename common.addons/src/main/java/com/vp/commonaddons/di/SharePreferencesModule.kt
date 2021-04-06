package com.vp.commonaddons.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module
class SharePreferencesModule {
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }
}