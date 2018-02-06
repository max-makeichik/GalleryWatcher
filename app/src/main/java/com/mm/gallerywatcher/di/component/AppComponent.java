package com.mm.gallerywatcher.di.component;

import android.content.Context;

import com.mm.gallerywatcher.di.annotation.AppScope;
import com.mm.gallerywatcher.di.module.ContextModule;

import dagger.Component;

/**
 * Created by Andrey V. Murzin on 26.07.17.
 */

@Component(modules = {ContextModule.class})
@AppScope
public interface AppComponent {
    Context context();
}
