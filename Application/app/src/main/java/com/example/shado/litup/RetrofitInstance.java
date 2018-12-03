package com.example.shado.litup;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jorren on 3/12/2018.
 */

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.0.223/litup_api/api/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
