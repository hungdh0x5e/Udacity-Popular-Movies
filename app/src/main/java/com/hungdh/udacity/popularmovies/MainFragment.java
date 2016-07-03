package com.hungdh.udacity.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hungdh.udacity.popularmovies.model.Movie;
import com.hungdh.udacity.popularmovies.model.MoviesResponse;
import com.hungdh.udacity.popularmovies.service.TheMovieDB;
import com.hungdh.udacity.popularmovies.service.TheMovieDBAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment implements OnRecyclerViewItemClickListener {

    private final String TAG = getClass().getSimpleName();

    private RecyclerView mRecyclerView;
    private List<Movie> mMovies = new ArrayList<>();
    private MovieAdapter mAdapter;

    private final String SORT_BY_POPULAR = "popular";
    private final String SORT_BY_RATE = "top_rated";

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.movie_list);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieAdapter(getActivity());
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);

        fetchMovies(SORT_BY_POPULAR);

        return view;
    }

    private void fetchMovies(String sortBy) {
        TheMovieDBAPI service = TheMovieDB.getService();
        Call<MoviesResponse> call = service.getMovies(sortBy, BuildConfig.THE_MOVIES_API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                Log.d(TAG, response.code() + "");
                if (response.isSuccessful()) {

                    MoviesResponse result = response.body();
                    mMovies = result.getMovies();
                    for (Movie movie : mMovies) {
                        mAdapter.addItem(movie);
                        Log.v(TAG, movie.getTitle());
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
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
    public void onClick(View v, int position) {
        Toast.makeText(getActivity(), "Position: " + position
                + " : " + mMovies.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
