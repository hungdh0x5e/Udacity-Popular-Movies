package com.hungdh.udacity.popularmovies.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hungdh.udacity.popularmovies.R;
import com.hungdh.udacity.popularmovies.model.Movie;

import static com.hungdh.udacity.popularmovies.database.MovieContract.MOVIE;

public class DetailFragment extends Fragment {

    private final String TAG = "AAA" + getClass().getSimpleName();

    private ImageView mPoster;
    private ImageView[] mRateStar = new ImageView[5];
    private TextView mTitle, mReleaseDate, mRating, mOverview;
    private Button mMarkAsFavorite, mRemoveFromFavorites, mWatchTrailer;
    private Movie mMovie;

    public DetailFragment() {
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

        Bundle arguments = getArguments();
        if (arguments != null) {
            mMovie = (Movie) arguments.getSerializable(MOVIE);
        }

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        getWidgetForm(view);
        fillData();

        return view;
    }

    private void getWidgetForm(View v) {
        mPoster = (ImageView) v.findViewById(R.id.movie_poster);
        mTitle = (TextView) v.findViewById(R.id.movie_title);
        mReleaseDate = (TextView) v.findViewById(R.id.movie_release_date);
        mOverview = (TextView) v.findViewById(R.id.movie_overview);

        mRateStar[0] = (ImageView) v.findViewById(R.id.rating_star1);
        mRateStar[1] = (ImageView) v.findViewById(R.id.rating_star2);
        mRateStar[2] = (ImageView) v.findViewById(R.id.rating_star3);
        mRateStar[3] = (ImageView) v.findViewById(R.id.rating_star4);
        mRateStar[4] = (ImageView) v.findViewById(R.id.rating_star5);
        mRating = (TextView) v.findViewById(R.id.movie_user_rating);

//        mMarkAsFavorite = (Button) v.findViewById(R.id.button_mark_as_favorite);
//        mRemoveFromFavorites = (Button) v.findViewById(R.id.button_remove_from_favorites);
//        mWatchTrailer = (Button) v.findViewById(R.id.button_watch_trailer);
    }

    private void fillData() {
        if (mMovie == null) {
            Log.e(TAG, "Movie is null");
            return;
        }
        Glide.with(getActivity())
                .load(mMovie.getPosterUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(mPoster);

        mTitle.setText(mMovie.getTitle());
        mReleaseDate.setText(mMovie.getReleaseDate());
        mOverview.setText(mMovie.getOverview());
        mRating.setText(mMovie.getUserRating() + "/10");
        setRatingStar(mMovie.getUserRating());
    }

    public void setRatingStar(float ratingInFloat) {
        int rating = Math.round(ratingInFloat);

        for (int i = 1; i <= rating; i++) {

            if (i % 2 == 1) {
                mRateStar[i / 2].setImageResource(R.drawable.ic_star_half_black_24dp);
            } else {
                mRateStar[i / 2 - 1].setImageResource(R.drawable.ic_star_black_24dp);
            }
        }
    }
}
