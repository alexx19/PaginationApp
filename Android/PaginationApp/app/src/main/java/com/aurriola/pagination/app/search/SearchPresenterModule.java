package com.aurriola.pagination.app.search;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Alexander Urriola.
 */
@Module
public class SearchPresenterModule {
    private final SearchMVP.View mView;

    public SearchPresenterModule(SearchMVP.View mView) {
        this.mView = mView;
    }


    @Provides
    SearchMVP.View provideContractView()
    {
        return mView;
    }

}
