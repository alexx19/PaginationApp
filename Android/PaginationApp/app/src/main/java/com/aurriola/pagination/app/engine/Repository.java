package com.aurriola.pagination.app.engine;


import com.aurriola.pagination.app.engine.apimodel.Result;

import io.reactivex.Observable;

/**
 * Created by Alexander Urriola.
 */
public interface Repository {
    Observable<Result> getPersona(int page);
    Observable<ModelResult> findPerson(String personName);
}
