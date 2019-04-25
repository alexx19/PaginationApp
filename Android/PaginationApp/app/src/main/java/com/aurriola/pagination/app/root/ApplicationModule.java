package com.aurriola.pagination.app.root;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private String TAG = "ApplicationModule";
    private Application application;
    //
    public ApplicationModule(Application app)
    {
        this.application = app;
    }

    @Provides //
    @Singleton // para acceder el contexto.
    public Context getProvideContext(){
        return application;
    }
}
