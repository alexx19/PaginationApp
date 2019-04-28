package com.aurriola.pagination.app.search;

import android.util.Log;

import com.aurriola.pagination.app.engine.ModelResult;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Alexander Urriola.
 */
public class SearchPresenter implements SearchMVP.Presenter {
    private String TAG = "SearchPresenter";

    private SearchMVP.Model model;
    private SearchMVP.View view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public PublishProcessor<Integer> publishProcessor = PublishProcessor.create();


    private Disposable subscription = null;


    public SearchPresenter(SearchMVP.Model model) {
        this.model = model;
    }

    @Override
    public void rxJavaUnsuscribe() {
        //se quita la subscripcion de rxjava.
        if (subscription!=null && !subscription.isDisposed())
        {
            subscription.dispose();
        }
        compositeDisposable.clear();
    }

    @Override
    public void setView(SearchMVP.View view) {
        this.view = view;
    }

    @Override
    public void findPerson(String personName) {

    }

    @Override
    public void loadLogin(int pagination) {
        subscription = model.result(pagination)
                .subscribeOn(Schedulers.io())//hilo en el segundo plano, para buscar los datos a una API o DB.
                .observeOn(AndroidSchedulers.mainThread())//Notifica a la vista en el hilo principal; al obtener la respuesta.
                //Se utiliza subscribeWith, para hacer la dessudscripcion
                .subscribeWith(new DisposableObserver<ModelResult>() {
                    @Override
                    public void onNext(ModelResult modelResult) {
                        if (view!=null)
                        {
                            view.updateDate(modelResult);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (view != null) {
                            view.dialogError("Error al descargar las peliculas.");
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (view!=null)
                        {
                            view.dialogError("Informacion descargada con exito.");
                            Log.d(TAG, "onComplete() >>> false");
                            view.loading(false);
                        }
                    }
                });

    }


    /*@Override
    public void loadLogin(int pagination) {
        subscription = model.result(pagination)
                .subscribeOn(Schedulers.io())//hilo en el segundo plano, para buscar los datos a una API o DB.
                .observeOn(AndroidSchedulers.mainThread())//Notifica a la vista en el hilo principal; al obtener la respuesta.
        //Se utiliza subscribeWith, para hacer la dessudscripcion
        .subscribeWith(new DisposableObserver<ModelResult>() {
                           @Override
                           public void onNext(ModelResult modelResult) {
                               if (view!=null)
                               {
                                   view.updateDate(modelResult);
                               }
                           }

                           @Override
                           public void onError(Throwable e) {
                               e.printStackTrace();
                               if (view != null) {
                                   view.dialogError("Error al descargar las peliculas.");
                               }
                           }

                           @Override
                           public void onComplete() {
                               if (view!=null)
                               {
                                   view.dialogError("Informacion descargada con exito.");
                                   Log.d(TAG, "onComplete >>> false");
                                   view.loading(false);
                               }
                           }
                       });

        publishProcessor.onNext(pagination);
    }*/


    @Override
    public void start() {

    }

    public void setPageNumber(int pageNumber)
    {
        Log.d(TAG," setPageNumber "+pageNumber);
        loadLogin(pageNumber);
    }
}
