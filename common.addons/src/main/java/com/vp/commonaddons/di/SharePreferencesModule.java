package com.vp.commonaddons.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;

@Module
public class SharePreferencesModule {

    @Provides
    Gson providesGson() {
        return new GsonBuilder().create();
    }
}
