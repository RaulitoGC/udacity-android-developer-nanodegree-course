package com.rguzman.popularmovie.presentation.detail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rguzman.popularmovie.R;
import com.rguzman.popularmovie.domain.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    public interface ListItemClickListener {
        void onListItemClick(Video video);
    }

    private ListItemClickListener listener;
    private List<Video> list = new ArrayList<>();

    public VideoAdapter(ListItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_video;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Video> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        private TextView txtVideoTitle;

        public VideoViewHolder(View itemView) {
            super(itemView);
            this.txtVideoTitle = itemView.findViewById(R.id.txt_video_title);

        }

        void bind(final Video video) {
            txtVideoTitle.setText(video.getName());
            itemView.setOnClickListener(v -> listener.onListItemClick(video));
        }
    }
}
