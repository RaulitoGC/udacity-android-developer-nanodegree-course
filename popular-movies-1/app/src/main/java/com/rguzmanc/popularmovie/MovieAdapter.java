package com.rguzmanc.popularmovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rguzmanc.popularmovie.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView movieImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            this.movieImageView = itemView.findViewById(R.id.movie_image);
        }

        void bind(final Movie movie) {
            Picasso.get()
                    .load(movie.getPosterPath())
                    .into(movieImageView);

            itemView.setOnClickListener(v -> listener.onListItemClick(movie));
        }
    }
}
