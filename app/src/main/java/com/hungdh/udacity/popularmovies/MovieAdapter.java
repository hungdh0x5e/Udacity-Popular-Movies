package com.hungdh.udacity.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hungdh.udacity.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungdh on 03/07/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> mMovies = new ArrayList<>();
    private LayoutInflater inflater;
    private final String TAG = this.getClass().getSimpleName();

    private OnRecyclerViewItemClickListener mListener;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    public void addItem(Movie movie){
        mMovies.add(movie);
        notifyItemInserted(mMovies.size());
    }

    public void clear(){
        mMovies.clear();
        notifyDataSetChanged();
    }

    public void setListener(OnRecyclerViewItemClickListener listener){
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_movie, parent, false);
        int gridColsNumber = 2;
        view.getLayoutParams().height = (int) (parent.getWidth() / gridColsNumber * Utility.POSTER_ASPECT_RATIO);

        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        Glide.with(mContext)
                .load(movie.getPosterUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mThumbnailView);

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mThumbnailView;
        OnRecyclerViewItemClickListener mListener;

        public ViewHolder(View view, OnRecyclerViewItemClickListener listener) {
            super(view);

            mThumbnailView = (ImageView) view.findViewById(R.id.thumbnail);
            mListener = listener;

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(v, getLayoutPosition());
                }
            });
        }


    }
}
