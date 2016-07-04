package com.hungdh.udacity.popularmovies.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hungdh.udacity.popularmovies.BuildConfig;
import com.hungdh.udacity.popularmovies.adapter.MovieAdapter;
import com.hungdh.udacity.popularmovies.R;
import com.hungdh.udacity.popularmovies.Utility;
import com.hungdh.udacity.popularmovies.model.Movie;
import com.hungdh.udacity.popularmovies.model.MoviesResponse;
import com.hungdh.udacity.popularmovies.service.TheMovieDB;
import com.hungdh.udacity.popularmovies.service.TheMovieDBAPI;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hungdh.udacity.popularmovies.database.MovieContract.MOVIE;


public class MainFragment extends Fragment implements MovieAdapter.Callback {

    private final String TAG = "AAA" + getClass().getSimpleName();

    private RecyclerView mRecyclerView;
    private ArrayList<Movie> mMovies = new ArrayList<>();
    private MovieAdapter mAdapter;
    private GridLayoutManager layoutManager;

    private ProgressBar progressBar;
    private TextView txtError;

    private final String SORT_BY_POPULAR = "popular";
    private final String SORT_BY_RATE = "top_rated";
    private final String KEY_DATA = "data";

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.movie_list);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(getActivity());
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        txtError = (TextView) view.findViewById(R.id.txt_error);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_DATA)) {
            mMovies = savedInstanceState.getParcelableArrayList(KEY_DATA);
            updateAdapter();
        } else {
            fetchMovies(SORT_BY_POPULAR);
        }

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v(TAG, "onSaveInstanceState");
        outState.putParcelableArrayList(KEY_DATA, mMovies);
        super.onSaveInstanceState(outState);
    }

    private void fetchMovies(String sortBy) {

        mRecyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        txtError.setVisibility(View.GONE);

        if(!Utility.networkAvaiable(getActivity())){
            progressBar.setVisibility(View.GONE);
            txtError.setVisibility(View.VISIBLE);
            txtError.setText("Network connection not avaiable!");
            return;
        }

        TheMovieDBAPI service = TheMovieDB.getService();
        Call<MoviesResponse> call = service.getMovies(sortBy, BuildConfig.THE_MOVIES_API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.d(TAG, response.code() + "");
                if (response.isSuccessful()) {
                    mAdapter.clear();
                    mMovies.clear();
                    MoviesResponse result = response.body();
                    mMovies = result.getMovies();
                    updateAdapter();
                    mRecyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                progressBar.setVisibility(View.GONE);
                txtError.setVisibility(View.VISIBLE);
                txtError.setText(t.toString());
            }
        });
    }

    private void updateAdapter() {
        for (Movie movie : mMovies) {
            mAdapter.addItem(movie);
//            Log.v(TAG, movie.getTitle());
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(View v, int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(MOVIE, (Serializable) mMovies.get(position));
        getActivity().startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_by_most_popular) {
            fetchMovies(SORT_BY_POPULAR);
        } else if (id == R.id.sort_by_top_rated) {
            fetchMovies(SORT_BY_RATE);
        }
        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }
}
