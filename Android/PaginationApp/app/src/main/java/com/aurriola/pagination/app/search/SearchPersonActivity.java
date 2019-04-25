package com.aurriola.pagination.app.search;

import android.app.FragmentTransaction;
import android.arch.lifecycle.ReportFragment;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.TextView;

import com.aurriola.pagination.app.R;
import com.aurriola.pagination.app.engine.ActivityUtils;
import com.aurriola.pagination.app.root.App;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchPersonActivity extends AppCompatActivity {
    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    @Inject
    SearchMVP.Presenter aPresenter;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    SearchFragment searchFragment = (SearchFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                    //fragment =  new SearchFragment.newInstance();
                    if (searchFragment == null) {
                        searchFragment = SearchFragment.newInstance();
                        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),searchFragment,R.id.contentFrame);
                    }

                    break;
                case R.id.navigation_search:
                    break;
                case R.id.navigation_queue:
                    break;
                case R.id.navigation_profile:
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);

        ((App)getApplication()).getComponent().searchActivityInject(this);


    }

    /*private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame, fragment);
        fragmentTransaction.commit();
    }*/
}
