package com.softxpert.datasource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.softxpert.remote.APIInterface;
import com.softxpert.remote.RetrofitClient;
import com.softxpert.responses.CarResponse;
import com.softxpert.utils.Constants;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarsDataSource extends PageKeyedDataSource<Long, CarResponse.Data> {

    private APIInterface apiInterface;
    public static MutableLiveData<String> carStatus = new MutableLiveData<>();

    public CarsDataSource() {
        apiInterface = RetrofitClient.getApi();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, CarResponse.Data> callback) {

        apiInterface.getCars((long) 1).enqueue(new Callback<CarResponse>() {
            @Override
            public void onResponse(Call<CarResponse> call, Response<CarResponse> response) {
                if (response.code() == 200) {
                    carStatus.postValue(Constants.DATA_LOADED);
                    List<CarResponse.Data> data;
                    if (response.body().getData().size() > 0) {
                        data = response.body().getData();
                    } else {
                        data = Collections.emptyList();
                    }
                    callback.onResult(data, null, (long) 2);
                } else {
                    carStatus.postValue(Constants.ERROR);
                }
            }

            @Override
            public void onFailure(Call<CarResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                if (t.getMessage().equals("timeout")) {
                    carStatus.postValue(Constants.NO_INTERNET);
                } else {
                    carStatus.postValue(Constants.ERROR);
                }
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, CarResponse.Data> callback) {
        apiInterface.getCars(params.key).enqueue(new Callback<CarResponse>() {
            @Override
            public void onResponse(Call<CarResponse> call, Response<CarResponse> response) {
                if (response.code() == 200) {
                    carStatus.postValue(Constants.DATA_LOADED);
                    List<CarResponse.Data> data;
                    if (response.body().getData().size() > 0) {
                        data = response.body().getData();
                    } else {
                        data = Collections.emptyList();
                    }
                    callback.onResult(data, params.key - 1);
                } else {
                    carStatus.postValue(Constants.ERROR);
                }
            }

            @Override
            public void onFailure(Call<CarResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                if (t.getMessage().equals("timeout")) {
                    carStatus.postValue(Constants.NO_INTERNET);
                } else {
                    carStatus.postValue(Constants.ERROR);
                }
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, CarResponse.Data> callback) {
        apiInterface.getCars(params.key).enqueue(new Callback<CarResponse>() {
            @Override
            public void onResponse(Call<CarResponse> call, Response<CarResponse> response) {
                if (response.code() == 200) {
                    carStatus.postValue(Constants.DATA_LOADED);
                    List<CarResponse.Data> data;
                    if (response.body().getData() != null) {

                        if (response.body().getData().size() > 0) {
                            data = response.body().getData();
                        } else {
                            data = Collections.emptyList();
                        }
                    } else {
                        data = Collections.emptyList();
                    }
                    callback.onResult(data, params.key + 1);
                } else {
                    carStatus.postValue(Constants.ERROR);
                }
            }

            @Override
            public void onFailure(Call<CarResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
                if (t.getMessage().equals("timeout")) {
                    carStatus.postValue(Constants.NO_INTERNET);
                } else {
                    carStatus.postValue(Constants.ERROR);
                }
            }
        });
    }
}
