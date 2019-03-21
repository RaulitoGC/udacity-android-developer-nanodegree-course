package com.rguzman.xyzreader.ui.list;

import android.app.LoaderManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rguzman.xyzreader.R;
import com.rguzman.xyzreader.data.ArticleLoader;
import com.rguzman.xyzreader.data.ItemsContract;
import com.rguzman.xyzreader.data.UpdaterService;
import com.rguzman.xyzreader.ui.detail.ArticleDetailActivity;

/**
 * An activity representing a list of Articles. This activity has different presentations for
 * handset and tablet-size devices. On handsets, the activity presents a list of items, which when
 * touched, lead to a {@link ArticleDetailActivity} representing item details. On tablets, the
 * activity presents a grid of items as cards.
 */
public class ArticleListActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, ArticleAdapter.OnItemClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        setTitle(R.string.app_name);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        mRecyclerView = findViewById(R.id.recycler_view);

        adapter = new ArticleAdapter(null);
        adapter.setHasStableIds(true);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        int columnCount = getResources().getInteger(R.integer.list_column_count);
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(this, columnCount);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        getLoaderManager().initLoader(0, null, this);

        if (savedInstanceState == null) {
            refresh();
        }
    }

    private void refresh() {
        startService(new Intent(this, UpdaterService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mRefreshingReceiver,
                new IntentFilter(UpdaterService.BROADCAST_ACTION_STATE_CHANGE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mRefreshingReceiver);
    }

    private BroadcastReceiver mRefreshingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (UpdaterService.BROADCAST_ACTION_STATE_CHANGE.equals(intent.getAction())) {
                boolean isRefreshing = intent.getBooleanExtra(UpdaterService.EXTRA_REFRESHING, false);
                updateRefreshingUI(isRefreshing);
            }
        }
    };

    private void updateRefreshingUI(boolean isRefreshing) {
        mSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.updateList(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerView.setAdapter(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(ImageView thumbnail, TextView title, TextView subtitle, long itemId) {


        Pair<View, String> p1 = new Pair<View, String>(thumbnail, getString(R.string.thumbnail_shared_element));
        Pair<View, String> p2 = new Pair<View, String>(title, getString(R.string.article_title_shared_element));
        Pair<View, String> p3 = new Pair<View, String>(subtitle, getString(R.string.article_subtitle_shared_element));

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, p1, p2, p3);


        startActivity(new Intent(Intent.ACTION_VIEW,
                ItemsContract.Items.buildItemUri(itemId)), options.toBundle());
    }
}

