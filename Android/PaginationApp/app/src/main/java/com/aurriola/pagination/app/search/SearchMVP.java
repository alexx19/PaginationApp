package com.aurriola.pagination.app.search;

import com.aurriola.pagination.app.BasePresenter;
import com.aurriola.pagination.app.BaseView;
import com.aurriola.pagination.app.engine.ModelResult;

import io.reactivex.Observable;

/**
 * Created by Alexander Urriola.
 */
public interface SearchMVP {
    /**
     * View: interfaz del login.
     */
    interface  View extends BaseView<Presenter>
    {
        void startLoading();
        void stopLoading();
        void loading(boolean status);
        void dialogError(String msg);
        void updateDate(ModelResult modelResult);
    }

    /**
     * Presenter: para la comunicacion, vista y el modelo.
     */
    interface Presenter extends BasePresenter
    {
        void rxJavaUnsuscribe();
        void setView(SearchMVP.View view);
        void findPerson(String personName);
        void loadLogin(int pagination);
        void setPageNumber(int pageNumber);
    }

    /**
     * Modelo de datos.
     */
    interface Model{
        Observable<ModelResult> result(Integer pagination);
    }
}
