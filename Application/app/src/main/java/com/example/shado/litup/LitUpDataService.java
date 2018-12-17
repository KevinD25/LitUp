package com.example.shado.litup;

import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Jorren on 3/12/2018.
 */

public interface LitUpDataService {

    @GET("user")
    Observable<User>getUser(@Header("Authorization") String firebaseToken, @Header("FirebaseId") String firebaseId);

    @GET("user/settings/{id}")
    Observable<Settings>getSettings(@Header("Authorization") String firebaseToken, @Path("id") int id);

    @POST("user/{id}")
    Observable<User>newUser(@Header("Authorization") String firebaseToken, @Header("FirebaseId") String firebaseId);

    @PUT("user/settings/{id}")
    Observable<Settings>updateSettings(@Path("id") int id, @Body Settings settings, @Header("Authorization") String firebaseToken);
}
