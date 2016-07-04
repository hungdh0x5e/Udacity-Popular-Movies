
package com.hungdh.udacity.popularmovies.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.hungdh.udacity.popularmovies.R;

import java.io.Serializable;

public class Movie implements Serializable, Parcelable {

    public static final String TAG = Movie.class.getSimpleName();

    @SerializedName("id")
    private long mId;
    @SerializedName("original_title")
    private String mTitle;
    @SerializedName("poster_path")
    private String mPoster;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("vote_average")
    private float mUserRating;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("backdrop_path")
    private String mBackdrop;

    // Only for createFromParcel
    private Movie() {
    }

    public Movie(long id, String title, String poster, String overview, float userRating,
                 String releaseDate, String backdrop) {
        mId = id;
        mTitle = title;
        mPoster = poster;
        mOverview = overview;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
        mBackdrop = backdrop;
    }

    protected Movie(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mPoster = in.readString();
        mOverview = in.readString();
        mUserRating = in.readFloat();
        mReleaseDate = in.readString();
        mBackdrop = in.readString();
    }


    @Nullable
    public String getTitle() {
        return mTitle;
    }

    public long getId() {
        return mId;
    }

    @Nullable
    public String getPosterUrl() {
        if (mPoster != null && !mPoster.isEmpty()) {
            return "http://image.tmdb.org/t/p/w185" + mPoster;
        }
        return null;
    }

    public String getPoster() {
        return mPoster;
    }


    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getOverview() {
        return mOverview;
    }

    public float getUserRating() {
        return mUserRating;
    }

    @Nullable
    public String getBackdropUrl(Context context) {
        if (mBackdrop != null && !mBackdrop.isEmpty()) {
            return context.getString(R.string.base_url_image_original) + mBackdrop;
        }
        return null;
    }

    public String getBackdrop() {
        return mBackdrop;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            Movie movie = new Movie();
            movie.mId = source.readLong();
            movie.mTitle = source.readString();
            movie.mPoster = source.readString();
            movie.mOverview = source.readString();
            movie.mUserRating = source.readFloat();
            movie.mReleaseDate = source.readString();
            movie.mBackdrop = source.readString();
            return movie;
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(mId);
        parcel.writeString(mTitle);
        parcel.writeString(mPoster);
        parcel.writeString(mOverview);
        parcel.writeFloat(mUserRating);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mBackdrop);
    }
}
