package com.hungdh.udacity.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by phoenix on 01/06/2016.
 */
public class TrailersResponse {
    @SerializedName("results")
    private ArrayList<Trailer> mTrailers = new ArrayList<>();

    public ArrayList<Trailer> getTrailers() {
        return mTrailers;
    }
}
