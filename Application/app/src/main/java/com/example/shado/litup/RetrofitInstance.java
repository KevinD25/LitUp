package com.example.shado.litup;

import retrofit2.adapter.rxjava2.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jorren on 3/12/2018.
 */

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://172.16.134.128/litup_api/api/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
