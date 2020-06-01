package com.rguzman.popularmovie.presentation.list;

import android.content.Context;
import android.media.midi.MidiOutputPort;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    public interface ListItemClickListener {
        void onListItemClick(Movie movie);
    }

    private ListItemClickListener listener;
    private List<Movie> list = new ArrayList<>();

    public MovieAdapter(ListItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_movie;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Movie> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearList() {
        this.list.clear();
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            this.movieImageView = itemView.findViewById(R.id.movie_image);
        }

        void bind(final Movie movie) {
            Glide.with(itemView)
                    .load(movie.getPosterPath())
                    .into(movieImageView);

            itemView.setOnClickListener(v -> listener.onListItemClick(movie));
        }
    }
}
