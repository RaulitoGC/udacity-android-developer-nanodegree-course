package com.rguzman.popularmovie.presentation.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> list = new ArrayList<>();

    public ReviewAdapter() {
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_review;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Review> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView txtReviewAuthor;
        private TextView txtReviewContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            this.txtReviewAuthor = itemView.findViewById(R.id.txt_review_author);
            this.txtReviewContent = itemView.findViewById(R.id.txt_review_content);
        }

        void bind(final Review review) {

            txtReviewAuthor.setText(review.getAuthor());
            txtReviewContent.setText(review.getContent());
        }
    }
}
