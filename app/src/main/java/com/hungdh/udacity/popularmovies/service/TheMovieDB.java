package com.hungdh.udacity.popularmovies.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hungdh on 03/07/2016.
 */
public class TheMovieDB {

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    static {
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
    }

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

    private static TheMovieDBAPI service = retrofit.create(TheMovieDBAPI.class);

    public static TheMovieDBAPI getService() {
        return service;
    }
}
