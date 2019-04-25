package com.aurriola.pagination.app.search;

import com.aurriola.pagination.app.engine.Repository;
import com.aurriola.pagination.app.engine.APIService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {
    /**
     * TODO: se agregan dentro de esta clase, todas las injecciones de dependencias realacionadas al MVP.
     */
    @Provides
    public SearchMVP.Presenter providePresenter(SearchMVP.Model model)
    {
        return new SearchPresenter(model);
    }

    @Provides
    public SearchMVP.Model provideModel(Repository repository)
    {
        return  new SearchModel(repository);
    }

    @Singleton //indica que sera solo un repositorio
    @Provides
    public Repository provideRepository(APIService apiService)
    {
        return new SearchRepository(apiService);
    }

    /*public SearchMVP.View provideView(SearchMVP.View view)
    {
        return new SearchFragment(view);
    }*/
}
