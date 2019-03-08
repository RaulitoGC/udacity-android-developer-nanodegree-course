package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity implements EndPointAsyncTask.EndPointCallback {

    public static final String NAME = "Raul";
    public TestActivityCallback callback;

    interface TestActivityCallback {
        void onResponse(String response);
    }

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));
    }

    public void setCallback(TestActivityCallback callback) {
        this.callback = callback;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    public void tellJoke(View view) {
        if (mIdlingResource != null) {
            this.mIdlingResource.setIdleState(false);
        }

        EndPointAsyncTask asyncTask = new EndPointAsyncTask(this);
        asyncTask.execute(NAME);
    }

    @Override
    public void onResponse(String response) {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
        if (callback != null) {
            callback.onResponse(response);
        }
    }
}
