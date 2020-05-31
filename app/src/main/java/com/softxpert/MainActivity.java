package com.softxpert;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softxpert.adapters.CarsPagedAdapter;
import com.softxpert.datasource.CarsDataSource;
import com.softxpert.responses.CarResponse;
import com.softxpert.utils.Constants;
import com.softxpert.utils.Utils;
import com.softxpert.viewModel.CarsViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.linear_no_internet)
    LinearLayout linearNoInternet;
    @BindView(R.id.linear_no_data)
    LinearLayout linearNoData;
    @BindView(R.id.linear_error)
    LinearLayout linearError;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private CarsPagedAdapter adapter;
    private CarsViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toggle(View.GONE, View.GONE, View.GONE, View.GONE);
                loadData();
            }
        });

        init();
        loadData();
    }


    private void loadData() {
        if (!Utils.isNetworkAvailable(this)) {
            refresh.setRefreshing(false);
            toggle(View.GONE, View.GONE, View.VISIBLE, View.GONE);
            return;
        }

        CarsDataSource.carStatus.postValue(" ");


        model.getAllCars().observe(this, new Observer<PagedList<CarResponse.Data>>() {
            @Override
            public void onChanged(PagedList<CarResponse.Data> data) {
                refresh.setRefreshing(false);
                toggle(View.VISIBLE, View.GONE, View.GONE, View.GONE);
                CarsDataSource.carStatus.observe(MainActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Log.d("TAG", "onChanged: "+s);
                        switch (s) {

                            case Constants.DATA_LOADED:

                                adapter.submitList(data);
                                adapter.notifyDataSetChanged();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (adapter.getItemCount() == 0) {
                                            toggle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
                                        } else {
                                            toggle(View.VISIBLE, View.GONE, View.GONE, View.GONE);
                                        }
                                    }
                                }, 100);
                                break;
                            case Constants.NO_DATA:
                                toggle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
                                break;
                            case Constants.NO_INTERNET:
                                toggle(View.GONE, View.GONE, View.VISIBLE, View.GONE);
                                break;
                            case Constants.ERROR:
                                toggle(View.GONE, View.GONE, View.GONE, View.VISIBLE);
                                break;
                        }
                    }
                });
            }
        });
    }

    private void init() {
        model = ViewModelProviders.of(this).get(CarsViewModel.class);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setItemAnimator(new DefaultItemAnimator());

        adapter = new CarsPagedAdapter(this);
        recycler.setAdapter(adapter);
    }

    private void toggle(int recyclerr, int noData, int noInternet, int error){
        recycler.setVisibility(recyclerr);
        linearNoData.setVisibility(noData);
        linearNoInternet.setVisibility(noInternet);
        linearError.setVisibility(error);
    }
}
