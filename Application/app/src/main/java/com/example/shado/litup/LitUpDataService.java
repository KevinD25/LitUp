package com.example.shado.litup;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Jorren on 3/12/2018.
 */

public interface LitUpDataService {
    @GET("settings/{id}")
    Call<Settings>getSettings(@Path("id") int id);
}
