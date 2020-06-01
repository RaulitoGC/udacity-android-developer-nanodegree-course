package com.rguzman.xyzreader.ui.list;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rguzman.xyzreader.R;
import com.rguzman.xyzreader.data.ArticleLoader;
import com.rguzman.xyzreader.ui.customview.DynamicHeightNetworkImageView;
import com.rguzman.xyzreader.ui.helper.ImageLoaderHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ImageView thumbnail, TextView title, TextView subtitle, int position);
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    // Use default locale format
    private SimpleDateFormat outputFormat = new SimpleDateFormat();
    // Most time functions can only handle 1902 - 2037
    private GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2, 1, 1);

    private Cursor mCursor;
    public OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArticleAdapter(Cursor cursor) {
        mCursor = cursor;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(ArticleLoader.Query._ID);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    private Date parsePublishedDate() {
        try {
            String date = mCursor.getString(ArticleLoader.Query.PUBLISHED_DATE);
            return dateFormat.parse(date);
        } catch (ParseException ex) {
            Log.e("Article Adapter", ex.getMessage());
            return new Date();
        }
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.titleView.setText(mCursor.getString(ArticleLoader.Query.TITLE));
        Date publishedDate = parsePublishedDate();
        if (!publishedDate.before(START_OF_EPOCH.getTime())) {

            holder.subtitleView.setText(Html.fromHtml(
                    DateUtils.getRelativeTimeSpanString(
                            publishedDate.getTime(),
                            System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                            DateUtils.FORMAT_ABBREV_ALL).toString()
                            + "<br/>" + " by "
                            + mCursor.getString(ArticleLoader.Query.AUTHOR)));
        } else {
            holder.subtitleView.setText(Html.fromHtml(
                    outputFormat.format(publishedDate)
                            + "<br/>" + " by "
                            + mCursor.getString(ArticleLoader.Query.AUTHOR)));
        }
        holder.thumbnailView.setImageUrl(
                mCursor.getString(ArticleLoader.Query.THUMB_URL),
                ImageLoaderHelper.getInstance(holder.thumbnailView.getContext()).getImageLoader());
        holder.thumbnailView.setAspectRatio(mCursor.getFloat(ArticleLoader.Query.ASPECT_RATIO));

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder.thumbnailView,
                holder.titleView, holder.subtitleView, holder.getAdapterPosition()));
    }

    void updateList(Cursor newCursor) {

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new CursorCallback<Cursor>(this.mCursor, newCursor) {
            @Override
            public boolean areRowContentsTheSame(Cursor oldCursor, Cursor newCursor) {
                boolean contentsTheSame = (oldCursor.getString(ArticleLoader.Query.TITLE).equals(newCursor.getString(ArticleLoader.Query.TITLE)));
                Log.i("Adapter", "contents the same: " + contentsTheSame);
                return contentsTheSame;
            }

            // as there is no unique id in the data, we simply regard the
            // article title as the unique id, as article titles are all different.
            @Override
            public boolean areCursorRowsTheSame(Cursor oldCursor, Cursor newCursor) {
                boolean cursorRowsTheSame = (oldCursor.getString(ArticleLoader.Query.TITLE).equals(newCursor.getString(ArticleLoader.Query.TITLE)));
                Log.i("Adapter", "cursor rows the same: " + cursorRowsTheSame);
                return cursorRowsTheSame;
            }
        });
        diffResult.dispatchUpdatesTo(this);
        this.mCursor = newCursor;
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        public DynamicHeightNetworkImageView thumbnailView;
        public TextView titleView;
        public TextView subtitleView;

        public ArticleViewHolder(View view) {
            super(view);
            thumbnailView = view.findViewById(R.id.thumbnail);
            titleView = view.findViewById(R.id.article_title);
            subtitleView = view.findViewById(R.id.article_subtitle);
        }
    }

    public abstract class CursorCallback<C extends Cursor> extends DiffUtil.Callback {
        private final C newCursor;
        private final C oldCursor;

        public CursorCallback(C newCursor, C oldCursor) {
            this.newCursor = newCursor;
            this.oldCursor = oldCursor;
        }

        @Override
        public int getOldListSize() {
            return oldCursor == null ? 0 : oldCursor.getCount();
        }

        @Override
        public int getNewListSize() {
            return newCursor == null ? 0 : newCursor.getCount();
        }

        @Override
        public final boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldCursor.getColumnCount() == newCursor.getColumnCount() && moveCursorsToPosition(oldItemPosition, newItemPosition) && areCursorRowsTheSame(oldCursor, newCursor);
        }


        @Override
        public final boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldCursor.getColumnCount() == newCursor.getColumnCount() && moveCursorsToPosition(oldItemPosition, newItemPosition) && areRowContentsTheSame(oldCursor, newCursor);
        }

        @Nullable
        @Override
        public final Object getChangePayload(int oldItemPosition, int newItemPosition) {
            moveCursorsToPosition(oldItemPosition, newItemPosition);
            return getChangePayload(newCursor, oldCursor);
        }

        @Nullable
        public Object getChangePayload(C newCursor, C oldCursor) {
            return null;
        }

        private boolean moveCursorsToPosition(int oldItemPosition, int newItemPosition) {
            boolean newMoved = newCursor.moveToPosition(newItemPosition);
            boolean oldMoved = oldCursor.moveToPosition(oldItemPosition);
            return newMoved && oldMoved;
        }

        /**
         * Cursors are already moved to positions where you should obtain data by row.
         * Checks if contents at row are same
         *
         * @param oldCursor Old cursor object
         * @param newCursor New cursor object
         * @return See DiffUtil
         */
        public abstract boolean areRowContentsTheSame(Cursor oldCursor, Cursor newCursor);

        /**
         * Cursors are already moved to positions where you should obtain data from row
         * Checks if rows are the same, ideally, check by unique id
         *
         * @param oldCursor Old cursor object
         * @param newCursor New cursor object
         * @return See DiffUtil
         */
        public abstract boolean areCursorRowsTheSame(Cursor oldCursor, Cursor newCursor);
    }
}
