package com.hungdh.udacity.popularmovies.service;

import com.hungdh.udacity.popularmovies.model.MoviesResponse;
import com.hungdh.udacity.popularmovies.model.ReviewsReponse;
import com.hungdh.udacity.popularmovies.model.TrailersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by phoenix on 26/05/2016.
 */
public interface TheMovieDBAPI {
    @GET("3/movie/{sort_by}")
    Call<MoviesResponse> getMovies(@Path("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/videos")
    Call<TrailersResponse> getTrailers(@Path("id") String movieId, @Query("api_key") String apiKey);

    @GET("3/movie/{id}/reviews")
    Call<ReviewsReponse> getReviews(@Path("id") String movieId, @Query("api_key") String apiKey);

}