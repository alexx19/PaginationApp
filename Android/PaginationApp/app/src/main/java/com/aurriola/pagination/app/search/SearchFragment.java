package com.aurriola.pagination.app.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aurriola.pagination.app.R;
import com.aurriola.pagination.app.adapter.DividerItemDecoration;
import com.aurriola.pagination.app.adapter.ResultAdapter;
import com.aurriola.pagination.app.engine.ModelResult;
import com.aurriola.pagination.app.root.App;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;


public class SearchFragment extends Fragment implements SearchMVP.View, TextWatcher {

    private String TAG = "SearchFragment";

    @BindView(R.id.recycle_list_person)
    RecyclerView recycle_list_person;
    @BindView(R.id.edtx_search)
    EditText edtx_search;

    private LinearLayoutManager mLayoutManager;

    @Inject
    SearchMVP.Presenter fPresenter;

    ProgressDialog progressDialog;

    private ResultAdapter resultAdapter;
    private List<ModelResult> modelResults = new ArrayList<>();


    private final int VISIBLE_THRESHOLD = 10;
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
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycle_list_person.setLayoutManager(mLayoutManager);

        resultAdapter = new ResultAdapter(modelResults);
        recycle_list_person.setAdapter(resultAdapter);

        //todas las celdas de igual tamanano
        recycle_list_person.setHasFixedSize(true);
 
        recycle_list_person.setItemAnimator(new DefaultItemAnimator());
        recycle_list_person.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 8));

        //Evento para detectar lo que el usuario esta escribiendo.
        edtx_search.addTextChangedListener(this);


        setUpLoadMoreListener();
    }


    /**
     * Método  para realizar la siguiente busqueda por pagina.
     */
    private void setUpLoadMoreListener() {
        recycle_list_person.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@android.support.annotation.NonNull RecyclerView recyclerView, int dx, int scrollDown) {
                super.onScrolled(recyclerView, dx, scrollDown);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                if (!loading) {
                    Log.d(TAG, "("+visibleItemCount+"+"+firstVisibleItemPosition+") >= "+totalItemCount+" && "+firstVisibleItemPosition+">= 0 && "+totalItemCount + " >= "+VISIBLE_THRESHOLD);
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= VISIBLE_THRESHOLD) {
                        pageNumer++;
                        fPresenter.setPageNumber(pageNumer);
                        loading = true;
                    }
                }

            }
        });
    }

    public void onResume()
    {
        super.onResume();
        fPresenter.setView(this);
        fPresenter.setPageNumber(pageNumer);
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
        progressDialog.setMessage(getString(R.string.wait));
        progressDialog.show();
    }

    @Override
    public void stopLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void loading(boolean status) {
        Log.d(TAG,"loading ===> "+status);
        loading = status;
    }

    @Override
    public void dialogError(String msg) {
        Log.e(TAG,msg);
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d(TAG,"onTextChanged() "+charSequence.toString().trim());
        resultAdapter.getFilter().filter(charSequence.toString().trim());

        //control para realizar la siguiente busqueda por pagina.
        loading = !edtx_search.getText().toString().trim().equals("");
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}
