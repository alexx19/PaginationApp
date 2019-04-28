package com.aurriola.pagination.app.search;

import android.util.Log;

import com.aurriola.pagination.app.engine.Repository;
import com.aurriola.pagination.app.engine.ModelResult;
import com.aurriola.pagination.app.engine.apimodel.Name;
import com.aurriola.pagination.app.engine.apimodel.Result;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Alexander Urriola.
 */
public class SearchModel implements SearchMVP.Model {
    private String TAG = "SearchModel";
    private Repository repository; //interface, para realizar los diferentes tipos de llamadas, a un bd local o un WS.

    public SearchModel(Repository repository) {
        this.repository = repository; //binding del respositorio. lo realiza dagger.
    }

    @Override
    public Observable<ModelResult> result(Integer pagination) {

      return   repository.getPersona(pagination).flatMap(new Function<Result, Observable<ModelResult>>() {
          @Override
          public Observable<ModelResult> apply(Result result) throws Exception {
              return Observable.just(new ModelResult(result.getName().toString().trim(), result.getLocation().toString().trim(), result.getPicture().getMedium()));
          }
      });
    }
}
