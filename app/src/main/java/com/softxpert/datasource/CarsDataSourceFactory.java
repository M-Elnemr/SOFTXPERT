package com.softxpert.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class CarsDataSourceFactory extends DataSource.Factory {

    private CarsDataSource carsDataSource;
    private MutableLiveData<CarsDataSource> liveData;

    public CarsDataSourceFactory() {
        liveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        carsDataSource = new CarsDataSource();

        liveData.postValue(carsDataSource);

        return carsDataSource;
    }

    public MutableLiveData<CarsDataSource> getLiveData() {
        return liveData;
    }

}
