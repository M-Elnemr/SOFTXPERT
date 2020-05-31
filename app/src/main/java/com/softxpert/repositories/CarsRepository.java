package com.softxpert.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.softxpert.datasource.CarsDataSourceFactory;
import com.softxpert.responses.CarResponse;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CarsRepository {
    private Executor executor;

    public CarsRepository() {
        executor = Executors.newFixedThreadPool(5);
    }

    public LiveData<PagedList<CarResponse.Data>> getCars(){

        CarsDataSourceFactory factory = new CarsDataSourceFactory();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(6)
                .setPageSize(6)
                .setPrefetchDistance(6)
                .build();

        return new LivePagedListBuilder<>(factory, config).setFetchExecutor(executor).build();
    }
}
