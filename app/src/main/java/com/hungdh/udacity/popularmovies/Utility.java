package com.hungdh.udacity.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hungdh on 03/07/2016.
 */
public class Utility {
    public static final float POSTER_ASPECT_RATIO = 1.5f;

    public static boolean networkAvaiable(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
