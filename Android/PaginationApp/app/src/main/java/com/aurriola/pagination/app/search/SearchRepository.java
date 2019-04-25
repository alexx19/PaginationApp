package com.aurriola.pagination.app.search;

import com.aurriola.pagination.app.engine.Repository;
import com.aurriola.pagination.app.engine.APIService;
import com.aurriola.pagination.app.engine.ModelResult;
import com.aurriola.pagination.app.engine.apimodel.PersonResponse;
import com.aurriola.pagination.app.engine.apimodel.Result;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by Alexander Urriola.
 */
public class SearchRepository implements Repository {
    private APIService apiService;
    private List<Result> results;

    public SearchRepository(APIService apiService) {
        this.apiService = apiService;
        this.results = new ArrayList<>();
    }

    @Override
    public Observable<Result> getPersona(int page) {

        Observable<PersonResponse> responseObservable = apiService.getPerson(page, "10");
        return responseObservable.concatMap(new Function<PersonResponse, Observable<Result>>() {
            @Override
            public Observable<Result> apply(PersonResponse personResponse) throws Exception {
                return Observable.fromIterable(personResponse.getResults());
            }
        }).doOnNext(new Consumer<Result>() {
            @Override
            public void accept(Result result) throws Exception {
                results.add(result);
            }
        });
    }

    @Override
    public Observable<ModelResult> findPerson(String personName) {
        return null;
    }
}
