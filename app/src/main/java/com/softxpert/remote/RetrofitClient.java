package com.softxpert.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static APIInterface apiInterface = null;
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://demo1286023.mockable.io/api/v1/";

    public static APIInterface getApi(){

        if(apiInterface == null) {

                HttpLoggingInterceptor loggingInterceptor =
                        new HttpLoggingInterceptor();
                loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .build();

                 retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                         .client(client)
                         .build();

            apiInterface = retrofit.create(APIInterface.class);
        }

        return apiInterface;
    }
}
