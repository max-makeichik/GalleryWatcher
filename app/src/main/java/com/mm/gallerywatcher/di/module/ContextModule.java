package com.mm.gallerywatcher.di.module;

import android.content.Context;

import com.mm.gallerywatcher.di.annotation.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }
    
    @Provides
    @AppScope
    Context provideContext() {
        return context;
    }
}