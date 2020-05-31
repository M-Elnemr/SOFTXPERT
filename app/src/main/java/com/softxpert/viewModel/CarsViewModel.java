package com.softxpert.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.softxpert.repositories.CarsRepository;
import com.softxpert.responses.CarResponse;

public class CarsViewModel extends AndroidViewModel {

    private CarsRepository carsRepository;
    private LiveData<PagedList<CarResponse.Data>> carsData;

    public CarsViewModel(@NonNull Application application) {
        super(application);

        carsRepository = new CarsRepository();
        carsData = carsRepository.getCars();
    }

    public LiveData<PagedList<CarResponse.Data>> getAllCars(){
        return carsData;
    }

}
