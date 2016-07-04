package com.hungdh.udacity.popularmovies.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.hungdh.udacity.popularmovies.R;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener {

    private final String TAG = "AAA" + getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
