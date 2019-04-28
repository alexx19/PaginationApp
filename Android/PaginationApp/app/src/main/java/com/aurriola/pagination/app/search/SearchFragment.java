package com.aurriola.pagination.app.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aurriola.pagination.app.R;
import com.aurriola.pagination.app.adapter.ResultAdapter;
import com.aurriola.pagination.app.engine.ModelResult;
import com.aurriola.pagination.app.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;



public class SearchFragment extends Fragment implements SearchMVP.View{

    private String TAG = "SearchFragment";

    @BindView(R.id.recycle_list_person)
    RecyclerView recycle_list_person;

    @Inject
    SearchMVP.Presenter fPresenter;

    ProgressDialog progressDialog;

    private ResultAdapter resultAdapter;
    private List<ModelResult> modelResults = new ArrayList<>();


    private int lastVisibleItem, totalItemCount;
    private final int VISIBLE_THRESHOLD = 1;
    private int pageNumer = 1;
    private boolean loading = false;

    public SearchFragment()
    {

    }



    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        ((App)getActivity().getApplication()).getComponent().searchfragment(this);

        resultAdapter = new ResultAdapter(modelResults);
        recycle_list_person.setAdapter(resultAdapter);
        recycle_list_person.setItemAnimator(new DefaultItemAnimator());
        //todas las celdas de igual tamanano
        recycle_list_person.setHasFixedSize(true);
        recycle_list_person.setLayoutManager(new LinearLayoutManager(getActivity()));


        setUpLoadMoreListener();
        return view;
    }

    private void setUpLoadMoreListener() {
        recycle_list_person.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@android.support.annotation.NonNull RecyclerView recyclerView, int dx, int scrollDown) {
                super.onScrolled(recyclerView, dx, scrollDown);


                Log.d(TAG,"scrollDown "+scrollDown);
                if (!loading && totalItemCount <=(lastVisibleItem + VISIBLE_THRESHOLD))
                {
                    pageNumer++;
                    fPresenter.setPageNumber(pageNumer);
                    loading = true;
                }
                /*if (scrollDown > 0) {
                    Log.d(TAG,"scrollDown");
                    fPresenter.loadLogin(2);
                }
                */
            }
        });
    }

    public void onResume()
    {
        super.onResume();
        fPresenter.setView(this);
        fPresenter.loadLogin(pageNumer);
        //fPresenter.start();
    }
    public void onStop()
    {
        super.onStop();
        fPresenter.rxJavaUnsuscribe();//se quita la subcripcion a RxJava
        modelResults.clear();
        resultAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void startLoading() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Buscando...");
        //progressDialog.setMessage(getResources().getString(R.string.dialog_details));
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void loading(boolean status) {
        Log.d(TAG,"Status ===> "+status);
        loading = status;
    }

    @Override
    public void dialogError(String msg) {
        Log.e(TAG,msg);
       /* Snackbar snackbar = Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackbar.show();*/
    }

    @Override
    public void updateDate(ModelResult modelResult) {
        modelResults.add(modelResult);
        resultAdapter.notifyItemInserted(modelResults.size()-1);//para el item en la vista, sera el ultimo item.
    }

    @Override
    public void setPresenter(@NonNull SearchMVP.Presenter presenter) {
        fPresenter = presenter;
    }
}
