package com.aurriola.pagination.app.engine;

import com.aurriola.pagination.app.engine.apimodel.PersonResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Alexander Urriola.
 */
public interface APIService {

    @GET("api")
    Observable<PersonResponse> getPerson(@Query("page") Integer page, @Query("results") String countResults);

}
