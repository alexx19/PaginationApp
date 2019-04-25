package com.aurriola.pagination.app.root;

import android.app.Application;

import com.aurriola.pagination.app.search.SearchModule;

public class App extends Application {
    private ApplicationComponent component;

    //Sobre escribiendo el metodo oncreate
    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))//modulo de aplicaciones.
                .searchModule(new SearchModule())
                .build();
    }



    public ApplicationComponent getComponent() {
        return component;
    }
}
