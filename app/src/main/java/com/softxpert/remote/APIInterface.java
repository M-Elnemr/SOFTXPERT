package com.softxpert.remote;

import com.softxpert.responses.CarResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("cars")
    Call<CarResponse> getCars(@Query("page") Long page);

}
