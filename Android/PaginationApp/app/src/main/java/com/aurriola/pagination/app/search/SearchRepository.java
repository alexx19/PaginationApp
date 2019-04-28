package com.aurriola.pagination.app.search;

import com.aurriola.pagination.app.engine.Repository;
import com.aurriola.pagination.app.engine.APIService;
import com.aurriola.pagination.app.engine.ModelResult;
import com.aurriola.pagination.app.engine.apimodel.PersonResponse;
import com.aurriola.pagination.app.engine.apimodel.Result;


import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Alexander Urriola.
 */
public class SearchRepository implements Repository {
    private APIService apiService;

    public SearchRepository(APIService apiService) {
        this.apiService = apiService;
    }

    /**
     * Método para el paginado
     * @param page número de pagina.
     * @return resultado del llamado a la API.
     */
    @Override
    public Observable<Result> getPersona(int page) {

        Observable<PersonResponse> responseObservable = apiService.getPerson(page, "10");
        return responseObservable.concatMap(new Function<PersonResponse, Observable<Result>>() {
            @Override
            public Observable<Result> apply(PersonResponse personResponse) throws Exception {
                return Observable.fromIterable(personResponse.getResults());
            }
        });
    }

    /**
     * Método para buscar remota.
     * @param personName busqueda.
     * @return
     */
    @Override
    public Observable<ModelResult> findPerson(String personName) {
        return null;
    }
}
