package com.aurriola.pagination.app.root;

import com.aurriola.pagination.app.engine.APIClientModule;
import com.aurriola.pagination.app.search.SearchFragment;
import com.aurriola.pagination.app.search.SearchModule;
import com.aurriola.pagination.app.search.SearchPersonActivity;
import com.aurriola.pagination.app.search.SearchPresenterModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton//el application componente tiene que se unico para toda la aplicacion, se anota con @Singlenton
//Con el componente se especifica los modulos de la aplicacion --> @Component
@Component(modules = {
        ApplicationModule.class,
        SearchModule.class,
        APIClientModule.class,
        SearchPresenterModule.class
})
public interface ApplicationComponent {

    void searchActivityInject(SearchPersonActivity searchPersonActivity);
    void searchfragment(SearchFragment searchPersonActivity);

}
